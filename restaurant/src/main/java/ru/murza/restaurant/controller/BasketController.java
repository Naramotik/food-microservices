package ru.murza.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.murza.foodmodel.models.Basket;
import ru.murza.restaurant.service.BasketService;

import java.util.List;

@RestController
@RequestMapping("/basket")
public class BasketController {
    @Autowired
    private BasketService basketService;

    @GetMapping
    public ResponseEntity<List<Basket>> findAll(){
        return new ResponseEntity<>(basketService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Basket> save(@RequestBody Basket basket){
        return new ResponseEntity<>(basketService.save(basket), HttpStatus.CREATED);
    }

    @DeleteMapping("/basketId")
    public ResponseEntity<?> delete(@PathVariable("basketId") Long basketId){
        basketService.deleteById(basketId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
