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
import ru.murza.foodmodel.models.Bonus;
import ru.murza.foodmodel.models.Consignment;
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
            summary = "Вывод всех корзин с использованием статуса",
            description = "Вывод всех корзин с использованием статуса",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping
    public ResponseEntity<List<Basket>> findAll(@RequestParam(name = "status", required = false) String basketStatus){
        return new ResponseEntity<>(basketService.findAll(basketStatus), HttpStatus.OK);
    }

    @Operation(
            summary = "Вывод всех корзин пользователя для истории",
            description = "Вывод всех корзин по id указанного пользователя",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping("/{client_id}")
    public ResponseEntity<Basket> findBasket(@PathVariable Long client_id){
        return new ResponseEntity<>(basketService.findBasket(client_id), HttpStatus.OK);
    }

    @Operation(
            summary = "Добавление блюд в корзину (происходит после оплаты) [ЭТО ТИПА СОЗДАНИЕ ЗАКАЗА]",
            description = "Добавление блюд в корзину (происходит после оплаты) [ЭТО ТИПА СОЗДАНИЕ ЗАКАЗА]",
            responses = @ApiResponse(description = "ACCEPTED", responseCode = "202")
    )
    @PostMapping("/dish/{client_id}")
    public ResponseEntity<Basket> addDishToBasket(@RequestBody List<Dish> dishes,
                                                  @PathVariable Long client_id){
        return new ResponseEntity<>(basketService.addDishToBasket(dishes, client_id), HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Изменение статуса заказа (ENGAGED или DONE)",
            description = "Изменение статуса заказа (ENGAGED или DONE)",
            responses = @ApiResponse(description = "ACCEPTED", responseCode = "202")
    )
    @PostMapping("/status/{basket_id}/{status}")
    public ResponseEntity<Basket> changeStatusBasket(@PathVariable("basket_id") Long basket_id,
                                                                @PathVariable("status") String status){
        return new ResponseEntity<>(basketService.changeStatus(basket_id, status), HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Удаление корзины",
            description = "Удаление корзины",
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


    @Operation(
            summary = "Установка процента, которыый будет начисляться от суммы заказа в бонусы",
            description = "Установка процента, которыый будет начисляться от суммы заказа в бонусы",
            responses = @ApiResponse(description = "ACCEPTED", responseCode = "202")
    )
    @PostMapping("/bonus/{percent}")
    public ResponseEntity<Bonus> changePercent(@PathVariable("percent") Long percent){
        return new ResponseEntity<>(basketService.changePercent(percent), HttpStatus.ACCEPTED);
    }

}
