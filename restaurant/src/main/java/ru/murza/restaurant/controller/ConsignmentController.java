package ru.murza.restaurant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.murza.foodmodel.models.Consignment;
import ru.murza.restaurant.dto.ConsignmentDTO;
import ru.murza.restaurant.service.ConsignmentService;
import ru.murza.restaurant.util.Mapper;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consignment")
@Tag(name = "Consignment")
public class ConsignmentController {
    private final ConsignmentService consignmentService;

    public ConsignmentController(ConsignmentService consignmentService) {
        this.consignmentService = consignmentService;
    }

    @Operation(
            summary = "Добавление экземпляра товара (огурцы владимирские 200гр)",
            description = "Добавление экземпляра товара (огурцы владимирские 200гр)",
            responses = @ApiResponse(description = "CREATED", responseCode = "201")
    )
    @PostMapping("/{ingredient}")
    public ResponseEntity<ConsignmentDTO> addConsignment(@RequestBody ConsignmentDTO consignmentDTO,
                                                         @PathVariable("ingredient") String ingredientTitle){
        Consignment consignment = Mapper.modelMapper.map(consignmentDTO, Consignment.class);
        consignment = consignmentService.save(consignment, ingredientTitle);
        consignmentDTO = Mapper.modelMapper.map(consignment, ConsignmentDTO.class);
        return new ResponseEntity<>(consignmentDTO, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Вывод всех товаров на складе",
            description = "Вывод всех товаров на складе",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping
    public ResponseEntity<List<Consignment>> findAll(){
        return new ResponseEntity<>(consignmentService.findAll(), HttpStatus.OK);
    }


    @Operation(
            summary = "Удаление ингредиента",
            description = "Ручное удаление ингредиента со склада",
            responses = @ApiResponse(description = "NO_CONTENT", responseCode = "204")
    )
    @DeleteMapping
    public ResponseEntity<?> delete(@PathVariable("consignmentId") Long consignmentId){
        consignmentService.deleteById(consignmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
