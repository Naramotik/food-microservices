package ru.murza.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.murza.restaurant.repository.IngredientRepository;
import ru.murza.saledelivery.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Ingredient> findAll(){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);
        return ingredients;
    }


    public Ingredient save(Ingredient ingredient){
        return ingredientRepository.save(ingredient);
    }

    public void deleteById(Long ingredientId){
        ingredientRepository.deleteById(ingredientId);
    }
}
