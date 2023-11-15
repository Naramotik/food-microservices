package ru.murza.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.murza.client.dto.ClientToSave;
import ru.murza.client.repository.ClientRepository;
import ru.murza.foodmodel.models.Client;
import ru.murza.foodmodel.models.ManagerInfo;
import ru.murza.foodmodel.models.Roles;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Client save(ClientToSave clientToSave){

        Client client = Client.builder()
                    .name(clientToSave.getName())
                    .number(clientToSave.getNumber())
                    .password(clientToSave.getPassword())
                    .managerInfo(new ManagerInfo(clientToSave.getSecondName(), clientToSave.getAddress(), clientToSave.getItn()))
                    .build();

        if (clientToSave.getItn() != null){
            client.setRole(new Roles(1L, null, null));
        } else {
            client.setRole(new Roles(2L, null, null));
        }
        return clientRepository.save(client);
    }

    public Optional<Client> getClient(Long clientId){
        return clientRepository.findById(clientId);
    }
}
