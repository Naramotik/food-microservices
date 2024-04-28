package ru.murza.restaurant.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.murza.foodmodel.enums.DishCategory;
import ru.murza.foodmodel.models.*;
import ru.murza.restaurant.repository.DishRepository;
import ru.murza.restaurant.repository.MeasureRepository;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DishTest {
    @InjectMocks
    private DishService dishService;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private CompositionService compositionService;

    @Test
    public void findAll_test() {
        Dish dish1 = new Dish(1L, 301.05, "Borsh", List.of(new Composition()), List.of(new Store()), List.of(new Basket()), DishCategory.MAIN_COURSE);
        Dish dish2 = new Dish(2L, 442.01, "NeBorsh", List.of(new Composition()), List.of(new Store()), List.of(new Basket()), DishCategory.MAIN_COURSE);
        Mockito.when(dishRepository.findAll()).thenReturn(List.of(dish1, dish2));
        Assertions.assertEquals(2, dishService.findAll().size());
    }

    @Test
    public void findByDishCategory_test() {
        Dish dish = new Dish(1L, 301.05, "Borsh", List.of(new Composition()), List.of(new Store()), List.of(new Basket()), DishCategory.MAIN_COURSE);
        Mockito.when(dishRepository.findByDishCategory(dish.getDishCategory())).thenReturn(List.of(dish));
        List<Dish> dishes = dishService.findByDishCategory(dish.getDishCategory());
        DishCategory dishCategory = dishes.stream().findFirst().get().getDishCategory();
        Assertions.assertEquals(DishCategory.MAIN_COURSE, dishCategory);
    }

    @Test
    public void save_test(){
        Dish dish = new Dish(1L, 301.05, "Borsh", List.of(new Composition()), List.of(new Store()), List.of(new Basket()), DishCategory.MAIN_COURSE);
        Mockito.when(dishRepository.save(dish)).thenReturn(dish);
        Assertions.assertEquals(dish, dishService.save(dish, List.of(new Composition())));
    }

    @Test
    public void findCompositionsFromDish_test() {
        Composition composition = new Composition(1L, 2.0, new Dish(), new Ingredient());
        Dish dish = new Dish(1L, 301.05, "Borsh", List.of(composition), List.of(new Store()), List.of(new Basket()), DishCategory.MAIN_COURSE);
        Mockito.when(compositionService.findByDishId(dish.getId())).thenReturn(List.of(composition));
        Assertions.assertEquals(List.of(composition), dishService.findCompositionsFromDish(1L));
    }

}
