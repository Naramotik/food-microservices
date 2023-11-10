package ru.murza.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.murza.restaurant.service.IngredientService;
import ru.murza.saledelivery.dto.IngredientDTO;
import ru.murza.saledelivery.models.Ingredient;
import ru.murza.saledelivery.util.Mapper;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public ResponseEntity<List<IngredientDTO>> findAll(){
        List<IngredientDTO> ingredientDTOList = ingredientService.findAll()
                .stream()
                .map(ingredient -> Mapper.modelMapper.map(ingredient, IngredientDTO.class))
                .toList();
        return new ResponseEntity<>(ingredientDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IngredientDTO> save(@RequestBody Ingredient ingredient){
        Ingredient savedIngredient = ingredientService.save(ingredient);
        IngredientDTO outIngredientDTO = Mapper.modelMapper.map(savedIngredient, IngredientDTO.class);
        return new ResponseEntity<>(outIngredientDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/ingredientId")
    public ResponseEntity<?> delete(@PathVariable("ingredientId") Long ingredientId){
        ingredientService.deleteById(ingredientId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
