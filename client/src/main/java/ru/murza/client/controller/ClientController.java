package ru.murza.client.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.murza.client.dto.ClientToSave;
import ru.murza.client.exception.ClientNotFoundException;
import ru.murza.client.repository.ClientRepository;

import ru.murza.client.service.ClientService;
import ru.murza.foodmodel.models.Client;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/client")
@Tag(name = "Client")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(
            summary = "Создание пользователя",
            description = "Создание пользователя",
            responses = @ApiResponse(description = "CREATED", responseCode = "201")
    )
    @PostMapping
    public ResponseEntity<Client> post(@Valid @RequestBody ClientToSave clientToSave){
        return new ResponseEntity<>(clientService.save(clientToSave), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Получение пользователя",
            description = "Получение пользователя по id",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping("/{client_id}")
    public ResponseEntity<Client> getClient(@PathVariable Long client_id) throws ClientNotFoundException {
        return new ResponseEntity<>(clientService.getClient(client_id), HttpStatus.OK);
    }

}
