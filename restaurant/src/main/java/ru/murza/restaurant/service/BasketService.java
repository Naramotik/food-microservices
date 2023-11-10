package ru.murza.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.murza.foodmodel.models.Basket;
import ru.murza.restaurant.repository.BasketRepository;


import java.util.ArrayList;
import java.util.List;

@Service
public class BasketService {

    @Autowired
    private BasketRepository orderRepository;

    public List<Basket> findAll(){
        List<Basket> baskets = new ArrayList<>();
        orderRepository.findAll().forEach(baskets::add);
        return baskets;
    }

    public Basket save(Basket basket){
        return orderRepository.save(basket);
    }

    public void deleteById(Long orderId){
        orderRepository.deleteById(orderId);
    }
}
