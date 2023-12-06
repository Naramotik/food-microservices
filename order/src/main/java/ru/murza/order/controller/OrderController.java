package ru.murza.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.murza.foodmodel.models.Basket;
import ru.murza.foodmodel.models.Order;
import ru.murza.order.dto.OrderToSave;
import ru.murza.order.exception.StatusNotFoundException;
import ru.murza.order.exception.UserNotFoundException;
import ru.murza.order.service.OrderService;

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
}
