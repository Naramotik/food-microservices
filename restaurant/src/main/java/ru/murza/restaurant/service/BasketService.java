package ru.murza.restaurant.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.murza.feignclients.client.ClientClient;
import ru.murza.foodmodel.models.Basket;
import ru.murza.foodmodel.models.Dish;
import ru.murza.restaurant.repository.BasketRepository;


import java.util.ArrayList;
import java.util.List;

@Service
public class BasketService {

    private final BasketRepository basketRepository;
    private final ClientClient clientClient;

    public BasketService(BasketRepository basketRepository, ClientClient clientClient) {
        this.basketRepository = basketRepository;
        this.clientClient = clientClient;
    }

    public List<Basket> findAll(){
        List<Basket> baskets = new ArrayList<>();
        basketRepository.findAll().forEach(baskets::add);
        return baskets;
    }

    public Basket findBasket(Long client_id){
        if (isUserPresent(client_id) && basketRepository.findByClientId(client_id).isPresent()){
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
        return clientClient.getClient(client_id).getBody().isPresent();
    }
}
