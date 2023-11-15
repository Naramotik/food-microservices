package ru.murza.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.murza.foodmodel.models.Basket;
import ru.murza.foodmodel.models.Dish;
import ru.murza.restaurant.service.BasketService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/basket")
public class BasketController {
    @Autowired
    private BasketService basketService;

    @GetMapping
    public ResponseEntity<List<Basket>> findAll(){
        return new ResponseEntity<>(basketService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/{client_id}") // Создание корзины для нового пользователя
    public ResponseEntity<Basket> save(@PathVariable Long client_id){
        // TODO Проверка в микросервис CLIENT есть ли такой id
        return new ResponseEntity<>(basketService.save(client_id), HttpStatus.CREATED);
    }

    @PostMapping("/dish/{client_id}") // Добавление блюда в корзину
    public ResponseEntity<Basket> addDishToBasket(@RequestBody Dish dish,
                                                  @PathVariable Long client_id){
        return new ResponseEntity<>(basketService.addDishToBasket(dish, client_id), HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/basketId")
    public ResponseEntity<?> delete(@PathVariable("basketId") Long basketId){
        basketService.deleteById(basketId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
