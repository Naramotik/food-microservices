package ru.murza.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.murza.foodmodel.models.Basket;
import ru.murza.foodmodel.models.Order;
import ru.murza.order.dto.OrderToSave;
import ru.murza.order.service.OrderService;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/{client_id}")
    public ResponseEntity<Order> saveOrder(@PathVariable Long client_id,
                                           @RequestBody OrderToSave orderToSave){
        return new ResponseEntity<>(orderService.saveOrder(client_id, orderToSave), HttpStatus.OK);
    }
}
