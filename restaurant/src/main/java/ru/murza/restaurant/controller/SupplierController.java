package ru.murza.restaurant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.murza.foodmodel.models.Supplier;
import ru.murza.restaurant.dto.SupplierDTO;
import ru.murza.restaurant.service.SupplierService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/supplier")
@Tag(name = "Supplier")
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController (SupplierService supplierService){
        this.supplierService = supplierService;
    }

    @Operation(
            summary = "Вывод всех поставщиков",
            description = "Вывод всех поставщиков",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping
    public ResponseEntity<List<Supplier>> findAllSuppliers (){
        return new ResponseEntity<>(supplierService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Вывод поставщика",
            description = "Вывод поставщика",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> findAllSuppliers (@PathVariable("id") Long id){
        return new ResponseEntity<>(supplierService.findOne(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Добавление нового поставщика",
            description = "Добавление нового поставщика",
            responses = @ApiResponse(description = "CREATED", responseCode = "201")
    )
    @PostMapping
    public ResponseEntity<Supplier> saveSupplier(@RequestBody Supplier supplier){
        return new ResponseEntity<>(supplierService.save(supplier), HttpStatus.CREATED);
    }




}
