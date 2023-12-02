package ru.murza.restaurant.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.murza.foodmodel.models.Ingredient;
import ru.murza.restaurant.dto.IngredientDTO;
import ru.murza.restaurant.service.IngredientService;
import ru.murza.restaurant.util.Mapper;


import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredient")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public ResponseEntity<List<Ingredient>> findAll(){
        return new ResponseEntity<>(ingredientService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IngredientDTO> save(@Valid @RequestBody Ingredient ingredient){
        Ingredient savedIngredient = ingredientService.save(ingredient);
        IngredientDTO outIngredientDTO = Mapper.modelMapper.map(savedIngredient, IngredientDTO.class);
        return new ResponseEntity<>(outIngredientDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/ingredientId")
    public ResponseEntity<?> delete(@PathVariable("ingredientId") Long ingredientId){
        ingredientService.deleteById(ingredientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
