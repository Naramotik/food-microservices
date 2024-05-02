package ru.murza.feignclients.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.murza.foodmodel.models.Basket;

@FeignClient(name = "restaurant")
public interface RestaurantClient {

    @DeleteMapping("/api/v1/basket/{basket_id}")
    public ResponseEntity<?> delete(@PathVariable(value = "basket_id") Long basket_id);


    @PostMapping("/api/v1/basket/{client_id}")
    public ResponseEntity<Basket> save(@PathVariable(value = "client_id") Long client_id);

}
