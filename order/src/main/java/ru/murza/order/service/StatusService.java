package ru.murza.order.service;

import org.springframework.stereotype.Service;
import ru.murza.foodmodel.models.Status;
import ru.murza.order.exception.StatusNotFoundException;
import ru.murza.order.repository.StatusRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {
    private StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }
    public Status save(Status status){
        return statusRepository.save(status);
    }
    public List<Status> getAll(){
        return (List<Status>) statusRepository.findAll();
    }
    public Status findByName(String title) throws StatusNotFoundException {
        if (statusRepository.findByName(title).isPresent()){
            return statusRepository.findByName(title).get();
        } else throw new StatusNotFoundException("Status not found");
    }
}
