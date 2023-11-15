package ru.murza.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.murza.client.dto.ClientToSave;
import ru.murza.client.repository.ClientRepository;

import ru.murza.client.service.ClientService;
import ru.murza.foodmodel.models.Client;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> post(@RequestBody ClientToSave clientToSave){
        return new ResponseEntity<>(clientService.save(clientToSave), HttpStatus.CREATED);
    }

    @GetMapping("/{client_id}")
    public ResponseEntity<Optional<Client>> getClient(@PathVariable Long client_id){
        return new ResponseEntity<>(clientService.getClient(client_id), HttpStatus.CREATED);
    }
}
