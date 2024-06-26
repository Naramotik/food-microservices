package ru.murza.restaurant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/ingredient")
@Tag(name = "Ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Operation(
            summary = "Вывод всех ингредиентов",
            description = "Вывод всех ингредиентов",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping
    public ResponseEntity<List<Ingredient>> findAll(){
        return new ResponseEntity<>(ingredientService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Вывод ингредиента по айди",
            description = "Вывод ингредиента по айди",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> findOne(@PathVariable("id") Long id){
        return new ResponseEntity<>(ingredientService.findById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Добавление нового ингредиента",
            description = "Добавление нового ингредиента",
            responses = @ApiResponse(description = "CREATED", responseCode = "201")
    )
    @PostMapping
    public ResponseEntity<Ingredient> save(@Valid @RequestBody Ingredient ingredient){
        return new ResponseEntity<>(ingredientService.save(ingredient), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Удаление ингредиента",
            description = "Удаление ингредиента",
            responses = @ApiResponse(description = "NO_CONTENT", responseCode = "204")
    )
    @DeleteMapping("/{ingredientId}")
    public ResponseEntity<?> delete(@PathVariable("ingredientId") Long ingredientId){
        ingredientService.deleteById(ingredientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
