package ru.murza.restaurant.controller;

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
public class CompositionController {

    @Autowired
    private CompositionService compositionService;

    @GetMapping
    public ResponseEntity<List<Composition>> findAll(){
        return new ResponseEntity<>(compositionService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CompositionDTO> save(@Valid @RequestBody DishCompositionDTO dishCompositionDTO){
        Composition newComposition = compositionService.save(dishCompositionDTO);
        CompositionDTO outCompositionDTO = Mapper.modelMapper.map(newComposition, CompositionDTO.class);
        return new ResponseEntity<>(outCompositionDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/compositionId")
    public ResponseEntity<?> delete(@PathVariable("compositionId") Long compositionId){
        compositionService.deleteById(compositionId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
