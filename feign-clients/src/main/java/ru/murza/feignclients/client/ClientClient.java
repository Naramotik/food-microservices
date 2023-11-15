package ru.murza.feignclients.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.murza.foodmodel.models.Client;

import java.util.Optional;

@FeignClient(name = "client")
public interface ClientClient {
    @GetMapping("/api/v1/client/{client_id}")
    public ResponseEntity<Optional<Client>> getClient(@PathVariable(value = "client_id") Long client_id);
}
