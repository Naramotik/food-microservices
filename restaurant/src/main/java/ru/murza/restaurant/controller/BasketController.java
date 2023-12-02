package ru.murza.restaurant.controller;

import jakarta.validation.Valid;
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

    @GetMapping // Вывод всех корзин
    public ResponseEntity<List<Basket>> findAll(){
        return new ResponseEntity<>(basketService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{client_id}") // Вывод корзины указанного пользователя
    public ResponseEntity<Basket> findBasket(@PathVariable Long client_id){
        return new ResponseEntity<>(basketService.findBasket(client_id), HttpStatus.OK);
    }

    @PostMapping("/{client_id}") // Создание корзины для нового пользователя
    public ResponseEntity<Basket> save(@PathVariable Long client_id){
        return new ResponseEntity<>(basketService.save(client_id), HttpStatus.CREATED);
    }

    @PostMapping("/dish/{client_id}") // Добавление блюда в корзину
    public ResponseEntity<Basket> addDishToBasket(@Valid @RequestBody Dish dish,
                                                  @PathVariable Long client_id){
        return new ResponseEntity<>(basketService.addDishToBasket(dish, client_id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{basket_id}") // Удаление корзиры (Вызывается после создания заказа)
    public ResponseEntity<?> delete(@PathVariable("basket_id") Long basket_id){
        basketService.deleteById(basket_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
