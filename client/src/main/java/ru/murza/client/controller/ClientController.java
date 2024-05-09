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

import java.util.Date;
import java.util.List;
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
    public ResponseEntity<Client> post(@RequestBody ClientToSave clientToSave){
        return new ResponseEntity<>(clientService.save(clientToSave), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Получение пользователя по id",
            description = "Получение пользователя по id",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping("/{client_id}")
    public ResponseEntity<Optional<Client>> getClient(@PathVariable Long client_id) throws ClientNotFoundException {
        return new ResponseEntity<>(clientService.getClient(client_id), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение пользователя по телефону",
            description = "Получение пользователя по телефону",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping("/login/{number}")
    public ResponseEntity<Optional<Client>> getClientByNumber(@PathVariable String number) throws ClientNotFoundException {
        return new ResponseEntity<>(clientService.getClientByNubmer(number), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение всех работников",
            description = "Получение всех работников",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping
    public ResponseEntity<List<Client>> getClient() throws ClientNotFoundException {
        return new ResponseEntity<>(clientService.getWorkers(), HttpStatus.OK);
    }

    @Operation(
            summary = "Списание бонусов",
            description = "Списание бонусов",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @PostMapping("/{id}/{countBonus}")
    public ResponseEntity<Client> minusBonus(@PathVariable("id") Long id,
                                             @PathVariable("countBonus") Long countBonus){
        return new ResponseEntity<>(clientService.minusBonus(id, countBonus), HttpStatus.OK);
    }


    @Operation(
            summary = "Начисление бонусов",
            description = "Начисление бонусов",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @PostMapping("/add/{id}/{countBonus}")
    public ResponseEntity<Client> addBonus(@PathVariable("id") Long id,
                                           @PathVariable("countBonus") Long countBonus){
        return new ResponseEntity<>(clientService.addBonus(id, countBonus), HttpStatus.OK);
    }




    @Operation(
            summary = "Добавление рабочего дня",
            description = "Добавление рабочего дня",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @PostMapping("/schedule/{id}")
    public ResponseEntity<Client> addToSchedule(@PathVariable("id") Long id,
                                                @RequestParam(name = "year") int year,
                                                @RequestParam(name = "month") int month,
                                                @RequestParam(name = "day") int day){
        return new ResponseEntity<>(clientService.addToSchedule(id, year, month, day), HttpStatus.OK);
    }



















    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   ЭТИ МЕТОДЫ ПОКА НЕ ИСПОЛЬЗУЮТСЯ
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/register")
    public String addNewUser(@RequestBody Client client){
        return clientService.saveUser(client);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody Client client){
        return clientService.generateToken(client.getNumber());
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        clientService.validateToken(token);
        return "token is valid";
    }

}
