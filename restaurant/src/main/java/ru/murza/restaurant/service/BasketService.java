package ru.murza.restaurant.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.murza.feignclients.client.ClientClient;
import ru.murza.foodmodel.enums.BasketStatus;
import ru.murza.foodmodel.models.*;
import ru.murza.restaurant.dto.IngredientsExpensesDTO;
import ru.murza.restaurant.repository.BasketRepository;
import ru.murza.restaurant.repository.BonusRepository;
import ru.murza.restaurant.repository.ConsignmentRepository;
import ru.murza.restaurant.repository.IngredientRepository;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@Transactional
public class BasketService {

    private final BasketRepository basketRepository;
    private final IngredientRepository ingredientRepository;
    private final ConsignmentRepository consignmentRepository;
    private final BonusRepository bonusRepository;
    private final ClientClient clientClient;

    public BasketService(BasketRepository basketRepository, IngredientRepository ingredientRepository, ConsignmentRepository consignmentRepository, BonusRepository bonusRepository, ClientClient clientClient) {
        this.basketRepository = basketRepository;
        this.ingredientRepository = ingredientRepository;
        this.consignmentRepository = consignmentRepository;
        this.bonusRepository = bonusRepository;
        this.clientClient = clientClient;
    }

    public List<Basket> findAll(String basketStatus){
        List<Basket> baskets = new ArrayList<>();
        if (basketStatus == null)
            baskets = (List<Basket>) basketRepository.findAll();
        else
        {
            if (basketStatus.equals(BasketStatus.PAID.name()))
                baskets = basketRepository.findByBasketStatus(BasketStatus.PAID);
            if (basketStatus.equals(BasketStatus.ENGAGED.name()))
                baskets = basketRepository.findByBasketStatus(BasketStatus.ENGAGED);
            if (basketStatus.equals(BasketStatus.DONE.name()))
                baskets = basketRepository.findByBasketStatus(BasketStatus.DONE);
        }
        return baskets;
    }

    public Basket findBasket(Long client_id){
        Optional<Basket> basket = basketRepository.findByClientId(client_id);
        if (isUserPresent(client_id) && basket.isPresent()){
            return basketRepository.findByClientId(client_id).get();
        } else  return null;
    }

    @Transactional
    public Basket save(Long client_id){
            Basket basket = new Basket();
            basket.setTotal_price((double) 0);
            basket.setClientId(client_id);
            basket.setBasketStatus(BasketStatus.PAID);
            return basketRepository.save(basket);
    }


    public Basket addDishToBasket(List<Dish> dishes, Long client_id){
        Basket basket;
        if(isUserPresent(client_id))
            basket = save(client_id);
        else {
            basket = save(0L);
        }
            basket.setDishes(dishes);
            Date now = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            basket.setAcceptance_date(now);
            Basket savedBasket = basketRepository.save(basket);
            savedBasket.setTotal_price(calculateCost(savedBasket));

            savedBasket.setBonus(bonusRepository.findFirstByOrderByIdDesc());
            checkConsignments(savedBasket.getId());
            return basketRepository.save(savedBasket);

    }

    public void deleteById(Long basketId){
        basketRepository.deleteById(basketId);
    }

    public double calculateCost(Basket basket){
        return basket.getDishes().stream().map(Dish::getCost).reduce((double) 0, Double::sum);
    }

    public boolean isUserPresent(Long client_id){
        if(clientClient.getClient(client_id) != null){
            return Objects.requireNonNull(clientClient.getClient(client_id).getBody()).isPresent();
        }
        else return false;
    }

    public IngredientsExpensesDTO ingredientsExpenses(Integer fromYear, Integer fromMonth, Integer fromDay,
                                                      Integer toYear,   Integer toMonth,   Integer toDay) {
        IngredientsExpensesDTO ingredientsExpensesDTO = new IngredientsExpensesDTO();
        HashMap<String, String> ingredientMeasure = new HashMap<>();
        HashMap<String, Double> ingredientExpense = new HashMap<>();
        LocalDate from = LocalDate.of(fromYear,fromMonth,fromDay);
        LocalDate to = LocalDate.of(toYear,toMonth,toDay);
        List<Basket> baskets = basketRepository.findByBasketStatusAndBetweenDates(BasketStatus.DONE,
                Date.from(from.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(to.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));


        baskets.forEach(basket -> {
            List<Dish> dishes = basket.getDishes();
            dishes.forEach(dish -> {
                List<Composition> compositions = dish.getCompositions();
                compositions.forEach(composition -> {
                    String ingredientTitle = composition.getIngredient().getTitle();
                    if (ingredientExpense.containsKey(ingredientTitle))
                        ingredientExpense.put(ingredientTitle, ingredientExpense.get(ingredientTitle) + composition.getCount());
                    else
                        ingredientExpense.put(ingredientTitle, composition.getCount());
                });
            });
        });
        ingredientsExpensesDTO.setExpenses(ingredientExpense);

        List<Ingredient> ingredients = (List<Ingredient>) ingredientRepository.findAll();
        ingredients.forEach(ingredient -> {
            ingredientMeasure.put(ingredient.getTitle(), ingredient.getMeasure().getType());
        });
        ingredientsExpensesDTO.setMeasures(ingredientMeasure);

        return ingredientsExpensesDTO;
    }

    public Basket changeStatus(Long basket_id, String basketStatus) {
        Basket basket = basketRepository.findById(basket_id).get();
        if (basketStatus.equals(BasketStatus.ENGAGED.name()))
            basket.setBasketStatus(BasketStatus.ENGAGED);

        HashMap<Ingredient, Double> ingredientExpense = new HashMap<>();
        if (basketStatus.equals(BasketStatus.DONE.name())){
            basket.setBasketStatus(BasketStatus.DONE);
        }
        return basketRepository.save(basket);
    }


    public void checkConsignments(Long basket_id){
        Basket basket = basketRepository.findById(basket_id).get();
        HashMap<Ingredient, Double> ingredientExpense = new HashMap<>();
        basket.getDishes()
                .forEach(dish -> dish.getCompositions()
                        .forEach(composition -> {
                            if(ingredientExpense.containsKey(composition.getIngredient())){
                                ingredientExpense.put(composition.getIngredient(), ingredientExpense.get(composition.getIngredient()) + composition.getCount());
                            } else ingredientExpense.put(composition.getIngredient(), composition.getCount());
                        }));

        for (Map.Entry<Ingredient, Double> entry : ingredientExpense.entrySet()) {
            Ingredient ingredient = entry.getKey();
            final Double[] value = {entry.getValue()};

            List<Consignment> allConsignments = consignmentRepository.findAllByIngredient(ingredient);

            Calendar today = Calendar.getInstance();
            today.clear(Calendar.HOUR); today.clear(Calendar.MINUTE); today.clear(Calendar.SECOND);
            Date todayDate = today.getTime();

            List<Consignment> consignmentsWithGoodDate = new ArrayList<>();
            allConsignments.forEach(consignment -> {
                if (consignment.getExpiration_date().compareTo(todayDate) > -1 && consignment.getCount() > 0) {
                    consignmentsWithGoodDate.add(consignment);
                    consignmentsWithGoodDate.sort(Comparator.comparing(Consignment::getExpiration_date));
                }
            });

            Optional<Double> consignmentCounter = consignmentsWithGoodDate.stream().map(Consignment::getCount).reduce(Double::sum);
            if (consignmentCounter.isPresent() && consignmentCounter.get() < value[0] || consignmentsWithGoodDate.isEmpty())
                throw new RuntimeException("Продукты просрочились или закончились");
            else {
                int index = 0;
                while (value[0] > 0.0){
                    Consignment lastDateConsignment = consignmentsWithGoodDate.get(index);
                    if (lastDateConsignment.getCount() > 0){
                        if (value[0] > lastDateConsignment.getCount()){
                            value[0] = value[0] - lastDateConsignment.getCount();
                            lastDateConsignment.setCount(0.0);
                            consignmentRepository.save(lastDateConsignment);
                            index = index + 1;
                        }
                        else {
                            lastDateConsignment.setCount(lastDateConsignment.getCount() - value[0]);
                            consignmentRepository.save(lastDateConsignment);
                            value[0] = 0.0;
                        }
                    }
                }
            }
        }
    }

    public Bonus changePercent(Long percent) {
        Bonus bonus = new Bonus();
        bonus.setPercent(percent);
        return bonusRepository.save(bonus);
    }

    public Basket findOne(Long id) {
        return basketRepository.findById(id).get();
    }
}
