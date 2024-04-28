package ru.murza.restaurant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.murza.foodmodel.models.Basket;
import ru.murza.foodmodel.models.Dish;
import ru.murza.restaurant.dto.IngredientsExpensesDTO;
import ru.murza.restaurant.service.BasketService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/basket")
@Tag(name = "Basket")
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @Operation(
            summary = "Вывод всех корзин",
            description = "Вывод всех корзин",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping
    public ResponseEntity<List<Basket>> findAll(){
        return new ResponseEntity<>(basketService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Вывод корзины пользователя",
            description = "Вывод корзины по id указанного пользователя",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping("/{client_id}")
    public ResponseEntity<Basket> findBasket(@PathVariable Long client_id){
        return new ResponseEntity<>(basketService.findBasket(client_id), HttpStatus.OK);
    }

    @Operation(
            summary = "Создание корзины нового пользователя",
            description = "Создание корзины для пользователя, который только что зарегистрировался",
            responses = @ApiResponse(description = "CREATED",responseCode = "201")
    )
    @PostMapping("/{client_id}")
    public ResponseEntity<Basket> save(@PathVariable Long client_id){
        return new ResponseEntity<>(basketService.save(client_id), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Добавление блюда в корзину",
            description = "Добавление блюда в корзину",
            responses = @ApiResponse(description = "ACCEPTED",responseCode = "202")
    )
    @PostMapping("/dish/{client_id}")
    public ResponseEntity<Basket> addDishToBasket(@RequestBody Dish dish,
                                                  @PathVariable Long client_id){
        return new ResponseEntity<>(basketService.addDishToBasket(dish, client_id), HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Удаление корзины",
            description = "Удаление корзины (Вызывается после создания заказа)",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @DeleteMapping("/{basket_id}")
    public ResponseEntity<?> delete(@PathVariable("basket_id") Long basket_id){
        basketService.deleteById(basket_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Просмотр продаж ингредиентов за промежуток времени",
            description = "Просмотр продаж ингредиентов за промежуток времени",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping("/expenses/{fromYear}/{fromMonth}/{fromDay}/{toYear}/{toMonth}/{toDay}")
    public ResponseEntity<IngredientsExpensesDTO> ingredientsExpenses(@PathVariable("fromYear") Integer fromYear, @PathVariable("fromMonth") Integer fromMonth,
                                                                      @PathVariable("fromDay") Integer fromDay, @PathVariable("toYear") Integer toYear,
                                                                      @PathVariable("toMonth") Integer toMonth, @PathVariable("toDay") Integer toDay){
        return new ResponseEntity<>(basketService.ingredientsExpenses(fromYear, fromMonth, fromDay, toYear, toMonth, toDay), HttpStatus.OK);
    }
}
