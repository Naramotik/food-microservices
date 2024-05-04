package ru.murza.client.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.murza.client.dto.ClientToSave;
import ru.murza.client.exception.ClientNotFoundException;
import ru.murza.client.repository.ClientRepository;
import ru.murza.feignclients.client.RestaurantClient;
import ru.murza.foodmodel.models.Client;
import ru.murza.foodmodel.models.Roles;
import ru.murza.foodmodel.models.WorkerInfo;

import java.util.Optional;

@Service
public class ClientService {
    private RestaurantClient restaurantClient;
    private final ClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    public ClientService(RestaurantClient restaurantClient, ClientRepository clientRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.restaurantClient = restaurantClient;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Client save(ClientToSave clientToSave){
        Client client = new Client();
        if (clientToSave.getName() != null){
            WorkerInfo workerInfo = WorkerInfo.builder()
                    .name(clientToSave.getName())
                    .secondName(clientToSave.getSecondName())
                    .itn(clientToSave.getItn())
                    .salary(clientToSave.getSalary())
                    .password(clientToSave.getPassword())
                    .build();

            client.setWorkerInfo(workerInfo);
            //client.setRole();
        }
        client.setBonus(0L);
        client.setNumber(clientToSave.getNumber());

        //Client savedClient = clientRepository.save(client);
        //restaurantClient.save(savedClient.getId());

        //TODO НАЗНАЧЕНИЕ РОЛЕЙ
//        if (clientToSave.getItn() != null){
//            client.setRole(new Roles(1L, null, null));
//        } else {
//            client.setRole(new Roles(2L, null, null));
//        }
        return clientRepository.save(client);
    }

    public Optional<Client> getClient(Long clientId) throws ClientNotFoundException {
        return clientRepository.findById(clientId);
    }

    public Client minusBonus(Long id, Long countBonus){
        Client client = clientRepository.findById(id).get();
        client.setBonus(client.getBonus() - countBonus);
        return clientRepository.save(client);
    }

    public Client addBonus(Long id, Long countBonus) {
        Client client = clientRepository.findById(id).get();
        client.setBonus(client.getBonus() + countBonus);
        return clientRepository.save(client);
    }










    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   ЭТИ МЕТОДЫ ПОКА НЕ ИСПОЛЬЗУЮТСЯ
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String saveUser(Client client){
//        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
        return "user added";
    }

    public String generateToken(String username){
        return jwtService.generateToken(username);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }

}
