package ru.murza.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.murza.foodmodel.enums.DishCategory;
import ru.murza.foodmodel.enums.DishStatus;
import ru.murza.foodmodel.models.Composition;
import ru.murza.foodmodel.models.Dish;
import ru.murza.foodmodel.models.Ingredient;
import ru.murza.restaurant.dto.CompositionToSaveDTO;
import ru.murza.restaurant.repository.CompositionRepository;
import ru.murza.restaurant.repository.DishRepository;
import ru.murza.restaurant.repository.IngredientRepository;


import java.util.ArrayList;
import java.util.List;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private CompositionService compositionService;

    @Autowired
    private CompositionRepository compositionRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

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

    public Dish save(Dish dish, List<CompositionToSaveDTO> compositionToSaveDTOS){

        List<Composition> compositions = new ArrayList<>();

        compositionToSaveDTOS.forEach(compositionToSaveDTO -> {
            Composition composition2 = new Composition();
            composition2.setCount(compositionToSaveDTO.getCount());
            Ingredient ingredient = ingredientRepository.findById(compositionToSaveDTO.getIngredientId()).get();
            composition2.setIngredient(ingredient);
            compositions.add(composition2);
        });


        Dish newDish = new Dish();
        newDish.setTitle(dish.getTitle());
        newDish.setCost(dish.getCost());
        newDish.setDiscount(dish.getDiscount());
        newDish.setCalorie(dish.getCalorie());
        newDish.setTechnologicalMap(dish.getTechnologicalMap());
        newDish.setDishCategory(dish.getDishCategory());
        newDish.setDishStatus(dish.getDishStatus());

        List<Composition> newCompositions = new ArrayList<>();
        compositions.forEach(composition -> newCompositions.add(compositionRepository.save(composition)));

        newDish.setCompositions(newCompositions);
        newCompositions.forEach(composition -> composition.setDish(newDish));
        return dishRepository.save(newDish);
    }

    public void deleteById(Long dishId){
        dishRepository.deleteById(dishId);
    }

    public Dish stopDish(Long dishId, String category) {
        Dish dish = dishRepository.findById(dishId).get();
        System.out.println(category);
        if (category == null){
            dish.setDishCategory(DishCategory.STOPPED);
        }

        else {
            if(category.equals(DishCategory.BEVERAGES.name()))
                dish.setDishCategory(DishCategory.BEVERAGES);
            if(category.equals(DishCategory.FIRST_COURSE.name()))
                dish.setDishCategory(DishCategory.FIRST_COURSE);
            if(category.equals(DishCategory.MAIN_COURSE.name()))
                dish.setDishCategory(DishCategory.MAIN_COURSE);
            if(category.equals(DishCategory.SWEET.name()))
                dish.setDishCategory(DishCategory.SWEET);
            if(category.equals(DishCategory.OTHER.name()))
                dish.setDishCategory(DishCategory.OTHER);
        }
        return dishRepository.save(dish);
    }

    public Dish setDiscount(Long dish_id, Long percent) {
        Dish dish = dishRepository.findById(dish_id).get();
        dish.setDiscount(percent);
        return dishRepository.save(dish);
    }

    public Dish findOne(Long id) {
        return dishRepository.findById(id).get();
    }
}
