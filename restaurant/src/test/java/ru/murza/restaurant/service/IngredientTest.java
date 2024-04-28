package ru.murza.restaurant.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.murza.foodmodel.models.Composition;
import ru.murza.foodmodel.models.Ingredient;
import ru.murza.foodmodel.models.Measure;
import ru.murza.restaurant.repository.IngredientRepository;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class IngredientTest {
    @Mock
    private IngredientRepository ingredientRepository;
    @InjectMocks
    private IngredientService ingredientService;

    @Test
    public void findAll_test() {
        Ingredient ingredient1 = new Ingredient(1L, "pure", List.of(new Composition()), new Measure());
        Ingredient ingredient2 = new Ingredient(2L, "kotleta", List.of(new Composition()), new Measure());
        Mockito.when(ingredientRepository.findAll()).thenReturn(List.of(ingredient1, ingredient2));
        Assertions.assertEquals(2, ingredientService.findAll().size());
    }

    @Test
    public void save_test(){
        Ingredient ingredient = new Ingredient(1L, "pure", List.of(new Composition()), new Measure());
        Mockito.when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
        Assertions.assertEquals(ingredient, ingredientService.save(ingredient));
    }

    @Test
    public void deleteById_Test() {
        Ingredient ingredient = new Ingredient(1L, "pure", List.of(new Composition()), new Measure());
        ingredientService.deleteById(ingredient.getId());
        Mockito.verify(ingredientRepository, Mockito.times(1)).deleteById(ingredient.getId());
    }
}
