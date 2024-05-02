package ru.murza.restaurant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.murza.foodmodel.models.Basket;
import ru.murza.foodmodel.models.Order;
import ru.murza.restaurant.dto.OrderToSave;
import ru.murza.restaurant.exception.StatusNotFoundException;
import ru.murza.restaurant.exception.UserNotFoundException;
import ru.murza.restaurant.service.OrderService;


import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@Tag(name = "Order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Operation(
            summary = "Создание заказа",
            description = "Создание заказа",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @PostMapping("/{client_id}")
    public ResponseEntity<Order> saveOrder(@PathVariable Long client_id,
                                           @Valid @RequestBody OrderToSave orderToSave) throws UserNotFoundException, StatusNotFoundException {
        return new ResponseEntity<>(orderService.saveOrder(client_id, orderToSave), HttpStatus.OK);
    }

    @Operation(
            summary = "Просмотр всех заказов",
            description = "Просмотр всех заказов",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping
    public ResponseEntity<List<Order>> findAll(){
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Просмотр популярных заказов",
            description = "Просмотр популярных заказов",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping("/popular")
    public ResponseEntity<HashMap<String, Integer>> findPopular(){
        return new ResponseEntity<>(orderService.findPopularDishes(), HttpStatus.OK);
    }

}
