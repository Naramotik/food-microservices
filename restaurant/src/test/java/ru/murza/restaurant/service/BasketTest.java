package ru.murza.restaurant.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.murza.feignclients.client.ClientClient;
import ru.murza.foodmodel.enums.DishCategory;
import ru.murza.foodmodel.models.*;
import ru.murza.restaurant.repository.BasketRepository;
import ru.murza.restaurant.repository.MeasureRepository;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BasketTest {
    @InjectMocks
    private BasketService basketService;
    @Mock
    private BasketRepository basketRepository;
    @Mock
    private ClientClient clientClient;

//    @Test
//    public void findAll_test() {
//        Basket basket1 = new Basket(1L, 1L, null, 200.00, null, null);
//        Basket basket2 = new Basket(2L, 1L, null, 500.00, null, null);
//        Mockito.when(basketRepository.findAll()).thenReturn(List.of(basket1, basket2));
//        Assertions.assertEquals(2, basketService.findAll().size());
//    }
//
//    @Test
//    public void findBasket_UserNotFound_test() {
//        Optional<Basket> empty = Optional.empty();
//        Mockito.when(clientClient.getClient(1L)).thenReturn(null);
//        Mockito.when(basketRepository.findByClientId(1L)).thenReturn(empty);
//        Assertions.assertNull(basketService.findBasket(1L));
//    }
//
//    @Test
//    public void addDishToBasket_noUserPresent_test() {
//        Dish dish = new Dish();
//        Mockito.when(clientClient.getClient(1L)).thenReturn(null);
//        Assertions.assertNull(basketService.addDishToBasket(dish, 1L));
//    }
}