package ru.murza.restaurant.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.murza.feignclients.client.ClientClient;
import ru.murza.foodmodel.enums.BasketStatus;
import ru.murza.foodmodel.models.Basket;
import ru.murza.foodmodel.models.Composition;
import ru.murza.foodmodel.models.Dish;
import ru.murza.foodmodel.models.Ingredient;
import ru.murza.restaurant.dto.IngredientsExpensesDTO;
import ru.murza.restaurant.repository.BasketRepository;
import ru.murza.restaurant.repository.IngredientRepository;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class BasketService {

    private final BasketRepository basketRepository;
    private final IngredientRepository ingredientRepository;
    private final ClientClient clientClient;

    public BasketService(BasketRepository basketRepository, IngredientRepository ingredientRepository, ClientClient clientClient) {
        this.basketRepository = basketRepository;
        this.ingredientRepository = ingredientRepository;
        this.clientClient = clientClient;
    }

    public List<Basket> findAll(){
        List<Basket> baskets = new ArrayList<>();
        basketRepository.findAll().forEach(baskets::add);
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
        if(isUserPresent(client_id)){
            Basket basket = new Basket();
            basket.setTotal_price((double) 0);
            basket.setClientId(client_id);
            return basketRepository.save(basket);
        } else return null;
    }

    @Transactional
    public Basket addDishToBasket(Dish dish, Long client_id){
        if(isUserPresent(client_id)){
            System.out.println("in 1");
            Basket basket;
            if (basketRepository.findByClientId(client_id).isPresent()) {
                basket = basketRepository.findByClientId(client_id).get();
            } else {
                basket = save(client_id);
            }
            List<Dish> dishes;
            if(basket.getDishes() != null){
                dishes = basket.getDishes();
            } else
                dishes = new ArrayList<>();
            dishes.add(dish);
            basket.setDishes(dishes);
            Basket savedBasket = basketRepository.save(basket);
            savedBasket.setTotal_price(calculateCost(savedBasket));
            return basketRepository.save(savedBasket);
        } else return null;
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
        List<Basket> baskets = basketRepository.findByBasketStatus(BasketStatus.DONE,
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
}
