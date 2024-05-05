package ru.murza.restaurant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.murza.foodmodel.models.Composition;
import ru.murza.restaurant.dto.CompositionDTO;
import ru.murza.restaurant.dto.DishCompositionDTO;
import ru.murza.restaurant.dto.DishCompositionsDTO;
import ru.murza.restaurant.service.CompositionService;
import ru.murza.restaurant.util.Mapper;

import java.util.List;

@RestController
@RequestMapping("/api/v1/composition")
@Tag(name = "Composition")
public class CompositionController {

    private final CompositionService compositionService;

    public CompositionController(CompositionService compositionService) {
        this.compositionService = compositionService;
    }

    @Operation(
            summary = "Вывод всех композиций",
            description = "Вывод всех композиций",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping
    public ResponseEntity<List<Composition>> findAll(){
        return new ResponseEntity<>(compositionService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Создание композиции",
            description = "Создание новой композиции",
            responses = @ApiResponse(description = "CREATED", responseCode = "201")
    )
    @PostMapping
    public ResponseEntity<CompositionDTO> save(@Valid @RequestBody DishCompositionDTO dishCompositionDTO){
        Composition newComposition = compositionService.save(dishCompositionDTO);
        CompositionDTO outCompositionDTO = Mapper.modelMapper.map(newComposition, CompositionDTO.class);
        return new ResponseEntity<>(outCompositionDTO, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Удаление композиции",
            description = "Удаление композиции",
            responses = @ApiResponse(description = "NO_CONTENT", responseCode = "204")
    )
    @DeleteMapping("/{compositionId}")
    public ResponseEntity<?> delete(@PathVariable("compositionId") Long compositionId){
        compositionService.deleteById(compositionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
