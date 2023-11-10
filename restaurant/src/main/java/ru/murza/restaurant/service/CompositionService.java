package ru.murza.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.murza.restaurant.repository.CompositionRepository;
import ru.murza.saledelivery.models.Composition;

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
        return compositionRepository.findAllByDishId(dishId);
    }

    public Composition save(Composition composition){
        System.out.println(composition.getIngredients());
        return compositionRepository.save(composition);
    }

    public void deleteById(Long compositionId){
        compositionRepository.deleteById(compositionId);
    }
}
