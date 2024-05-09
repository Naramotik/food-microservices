package ru.murza.client.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.murza.client.dto.ClientToSave;
import ru.murza.client.exception.ClientNotFoundException;
import ru.murza.client.repository.ClientRepository;
import ru.murza.client.repository.ScheduleRepository;
import ru.murza.feignclients.client.RestaurantClient;
import ru.murza.foodmodel.enums.BasketStatus;
import ru.murza.foodmodel.models.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private RestaurantClient restaurantClient;
    private final ClientRepository clientRepository;
    private final ScheduleRepository scheduleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    public ClientService(RestaurantClient restaurantClient, ClientRepository clientRepository, ScheduleRepository scheduleRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.restaurantClient = restaurantClient;
        this.clientRepository = clientRepository;
        this.scheduleRepository = scheduleRepository;
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

        if (clientToSave.getItn() != null){
            client.setRole(new Roles(1L, null, null));
        } else {
            client.setRole(new Roles(2L, null, null));
        }
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


    public Client addToSchedule(Long id, int year, int month, int day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        Date date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Schedule schedule = new Schedule();
        schedule.setDate(date);
        Client client = clientRepository.findById(id).get();
        client.getSchedules().add(schedule);
        schedule.setClient(client);
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


    public List<Client> getWorkers() {
        List<Client> clients = (List<Client>) clientRepository.findAll();
        return clients.stream().filter(client -> client.getWorkerInfo() != null).toList();
    }

    public Optional<Client> getClientByNubmer(String number) {
        return clientRepository.findByNumber(number);
    }
}
