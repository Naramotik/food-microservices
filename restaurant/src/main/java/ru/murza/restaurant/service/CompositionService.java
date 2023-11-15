package ru.murza.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.murza.foodmodel.models.Composition;
import ru.murza.foodmodel.models.Dish;
import ru.murza.restaurant.dto.DishCompositionDTO;
import ru.murza.restaurant.dto.DishCompositionsDTO;
import ru.murza.restaurant.repository.CompositionRepository;


import java.util.ArrayList;
import java.util.List;

@Service
public class CompositionService {

    @Autowired
    private CompositionRepository compositionRepository;

    public List<Composition> findAll(){
        List<Composition> compositions = new ArrayList<>();
        compositionRepository.findAll().forEach(compositions::add);
        return compositions;
    }

    public List<Composition> findByDishId(Long dishId){
        return compositionRepository.findAllByDish_id(dishId);
    }

    public Composition save(DishCompositionDTO dishCompositionDTO){
        Composition composition = dishCompositionDTO.getComposition();
        Dish dish = dishCompositionDTO.getDish();
        composition.setDish(dish);
        return compositionRepository.save(composition);
    }

    public void deleteById(Long compositionId){
        compositionRepository.deleteById(compositionId);
    }
}
