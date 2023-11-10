package ru.murza.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.murza.foodmodel.enums.DishCategory;
import ru.murza.foodmodel.models.Composition;
import ru.murza.foodmodel.models.Dish;
import ru.murza.restaurant.dto.CompositionDTO;
import ru.murza.restaurant.dto.DishCompositionsDTO;
import ru.murza.restaurant.dto.DishDTO;
import ru.murza.restaurant.service.DishService;
import ru.murza.restaurant.util.Mapper;

import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping
    public ResponseEntity<List<DishDTO>> findAll(){
        List<DishDTO> dishDTO = dishService.findAll()
                .stream()
                .map(dish -> Mapper.modelMapper.map(dish, DishDTO.class))
                .toList();
        return new ResponseEntity<>(dishDTO, HttpStatus.OK);
    }

    @GetMapping("/menu")
    public ResponseEntity<List<DishDTO>> findByCategory(@RequestParam("category") String category){
        DishCategory dishCategory = null;

        for(DishCategory enumCategory : DishCategory.values()){
            if (category.equals(enumCategory.toString()))
                dishCategory = enumCategory;                                                                            //TODO EXCEPTION
        }

        if(dishCategory == null)
            throw new RuntimeException("Category not found");
        List<Dish> dishes = dishService.findByDishCategory(dishCategory);
        List<DishDTO> dishesDTO = dishes
                .stream()
                .map(dish -> Mapper.modelMapper.map(dish, DishDTO.class))
                .toList();
        return new ResponseEntity<>(dishesDTO, HttpStatus.UPGRADE_REQUIRED);
    }

    @GetMapping("/{dishId}")
    public ResponseEntity<List<CompositionDTO>> getCompositions(@PathVariable("dishId")Long dishId){
        List<Composition> compositionList = dishService.findCompositionsFromDish(dishId);
        List<CompositionDTO> compositionDTOList = compositionList
                .stream()
                .map(composition -> Mapper.modelMapper.map(composition, CompositionDTO.class))
                .toList();
        return new ResponseEntity<>(compositionDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DishDTO> save(@RequestBody DishCompositionsDTO dishCompositionsDTO){
        Dish dish = dishCompositionsDTO.getDish();
        List<Composition> compositions = dishCompositionsDTO.getCompositions();
        DishDTO dishDTO = Mapper.modelMapper.map(dishService.save(dish, compositions), DishDTO.class);
        return new ResponseEntity<>(dishDTO, HttpStatus.CREATED);
    }

//    @PutMapping("/ingredients")
//    public ResponseEntity<List<IngredientDTO>> getIngredients

    @DeleteMapping("/dishId")
    public ResponseEntity<?> delete(@PathVariable("dishId") Long dishId){
        dishService.deleteById(dishId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
