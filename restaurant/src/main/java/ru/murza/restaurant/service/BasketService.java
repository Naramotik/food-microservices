package ru.murza.restaurant.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.murza.feignclients.client.ClientClient;
import ru.murza.foodmodel.models.Basket;
import ru.murza.foodmodel.models.Client;
import ru.murza.foodmodel.models.Dish;
import ru.murza.restaurant.repository.BasketRepository;


import java.util.ArrayList;
import java.util.List;

@Service
public class BasketService {

    private BasketRepository basketRepository;

    private ClientClient clientClient;

    public BasketService(BasketRepository basketRepository, ClientClient clientClient) {
        this.basketRepository = basketRepository;
        this.clientClient = clientClient;
    }

    public List<Basket> findAll(){
        List<Basket> baskets = new ArrayList<>();
        basketRepository.findAll().forEach(baskets::add);
        return baskets;
    }

    public Basket save(Long client_id){
        Basket basket = new Basket();
        basket.setTotal_price((double) 0);
        basket.setClientId(client_id);
        return basketRepository.save(basket);
    }

    @Transactional
    public Basket addDishToBasket(Dish dish, Long client_id){

        if(clientClient.getClient(client_id).getBody().isPresent()){
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

    public void deleteById(Long orderId){
        basketRepository.deleteById(orderId);
    }

    public double calculateCost(Basket basket){
        return basket.getDishes().stream().map(Dish::getCost).reduce((double) 0, Double::sum);
    }


}
