package ru.murza.restaurant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/dish")
@Tag(name = "Dish")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @Operation(
            summary = "Вывод всех блюд",
            description = "Вывод всех блюд",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping
    public ResponseEntity<List<Dish>> findAll(){
        return new ResponseEntity<>(dishService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Вывод всех блюд по категории",
            description = "Вывод всех блюд выбранной категории",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping("/menu")
    public ResponseEntity<List<DishDTO>> findByCategory(@RequestParam("category") String category){
        DishCategory dishCategory = null;

        for(DishCategory enumCategory : DishCategory.values()){
            if (category.equals(enumCategory.toString()))
                dishCategory = enumCategory;
        }
        if(dishCategory == null)
            throw new RuntimeException("Category not found");

        List<Dish> dishes = dishService.findByDishCategory(dishCategory);
        List<DishDTO> dishesDTO = dishes
                .stream()
                .map(dish -> Mapper.modelMapper.map(dish, DishDTO.class))
                .toList();
        return new ResponseEntity<>(dishesDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Вывод композиций выбранного блюда",
            description = "Вывод композиций выбранного блюда",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping("/{dishId}")
    public ResponseEntity<List<CompositionDTO>> getCompositions(@PathVariable("dishId")Long dishId){
        List<Composition> compositionList = dishService.findCompositionsFromDish(dishId);
        List<CompositionDTO> compositionDTOList = compositionList
                .stream()
                .map(composition -> Mapper.modelMapper.map(composition, CompositionDTO.class))
                .toList();
        return new ResponseEntity<>(compositionDTOList, HttpStatus.OK);
    }

    @Operation(
            summary = "Добавление нового блюда",
            description = "Добавление нового блюда",
            responses = @ApiResponse(description = "CREATED", responseCode = "201")
    )
    @PostMapping
    public ResponseEntity<DishDTO> save(@RequestBody DishCompositionsDTO dishCompositionsDTO){
        System.out.println(dishCompositionsDTO);
        Dish dish = dishCompositionsDTO.getDish();
        List<Composition> compositions = dishCompositionsDTO.getCompositions();
        DishDTO dishDTO = Mapper.modelMapper.map(dishService.save(dish, compositions), DishDTO.class);
        return new ResponseEntity<>(dishDTO, HttpStatus.CREATED);
    }

//    @PutMapping("/ingredients")
//    public ResponseEntity<List<IngredientDTO>> getIngredients

    @Operation(
            summary = "Удаление блюда",
            description = "Удаление блюда по id",
            responses = @ApiResponse(description = "NO_CONTENT", responseCode = "204")
    )
    @DeleteMapping("/dishId")
    public ResponseEntity<?> delete(@PathVariable("dishId") Long dishId){
        dishService.deleteById(dishId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(
            summary = "БЕЗ ПАРАМЕТРА CATEGORY - БЛЮДО ДОБАВИТСЯ В СТОП-ЛИСТ" +
                      "ЕСЛИ C ПАРАМЕТРОМ - НАЗНАЧИТСЯ КАТЕГОРИЯ, КОТОРАЯ ПЕРЕДАЕТСЯ В ПАРАМЕТРЕ И УБЕРЕТСЯ ИЗ СТОП-ЛИСТА ЕСЛИ ОНО ТАМ БЫЛО",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @PostMapping("/{dishId}")
    public ResponseEntity<Dish> stopList(@PathVariable("dishId") Long dishId,
                                         @RequestParam(required = false) String category){
        return new ResponseEntity<>(dishService.stopDish(dishId, category), HttpStatus.OK);
    }
}
