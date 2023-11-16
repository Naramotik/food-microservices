package ru.murza.feignclients.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "restaurant")
public interface RestaurantClient {

    @DeleteMapping("/api/v1/basket/{basket_id}")
    public ResponseEntity<?> delete(@PathVariable(value = "basket_id") Long basket_id);
}
