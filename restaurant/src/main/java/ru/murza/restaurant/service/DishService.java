package ru.murza.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.murza.restaurant.repository.DishRepository;
import ru.murza.saledelivery.enums.DishCategory;
import ru.murza.saledelivery.models.Composition;
import ru.murza.saledelivery.models.Dish;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private CompositionService compositionService;

    public List<Dish> findAll(){
        List<Dish> dishes = new ArrayList<>();
        dishRepository.findAll().forEach(dishes::add);
        return dishes;
    }

    public List<Dish> findByDishCategory(DishCategory dishCategory){
        return dishRepository.findByDishCategory(dishCategory);
    }

    public List<Composition> findCompositionsFromDish(Long dishId){
        return compositionService.findByDishId(dishId);
    }

    public Dish save(Dish dish, List<Composition> compositions){
        dish.setCompositions(compositions);
        return dishRepository.save(dish);
    }

    public void deleteById(Long dishId){
        dishRepository.deleteById(dishId);
    }
}
