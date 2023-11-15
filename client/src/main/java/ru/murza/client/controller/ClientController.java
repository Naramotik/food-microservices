package ru.murza.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.murza.client.repository.ClientRepository;

import ru.murza.foodmodel.models.Client;


@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    ClientRepository clientRepository;
    @GetMapping
    public String hello(){
        return "hello";
    }
    @GetMapping("/c")
    public void post(){
        Client client = new Client(1L,"dima", "80080", "passs",null,null);
        clientRepository.save(client);
    }
}
