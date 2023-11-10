package ru.murza.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.murza.foodmodel.models.Composition;
import ru.murza.restaurant.dto.CompositionDTO;
import ru.murza.restaurant.service.CompositionService;
import ru.murza.restaurant.util.Mapper;

import java.util.List;

@RestController
@RequestMapping("/composition")
public class CompositionController {

    @Autowired
    private CompositionService compositionService;

    @GetMapping
    public ResponseEntity<List<CompositionDTO>> findAll(){
        List<CompositionDTO> compositionDTOList = compositionService.findAll()
                .stream()
                .map(composition -> Mapper.modelMapper.map(composition, CompositionDTO.class))
                .toList();
        return new ResponseEntity<>(compositionDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CompositionDTO> save(@RequestBody Composition composition){
        Composition newComposition = compositionService.save(composition);
        CompositionDTO outCompositionDTO = Mapper.modelMapper.map(newComposition, CompositionDTO.class);
        return new ResponseEntity<>(outCompositionDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/compositionId")
    public ResponseEntity<?> delete(@PathVariable("compositionId") Long compositionId){
        compositionService.deleteById(compositionId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
