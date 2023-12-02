package ru.murza.order.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.murza.foodmodel.models.Status;
import ru.murza.order.exception.StatusNotFoundException;
import ru.murza.order.service.StatusService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/status")
public class StatusController {
    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @PostMapping // Создание нового статуса
    public ResponseEntity<Status> save(@Valid @RequestBody Status status){
        return new ResponseEntity<>(statusService.save(status), HttpStatus.CREATED);
    }

    @GetMapping // Вывод всех статусов
    public ResponseEntity<List<Status>> getAll(){
        return new ResponseEntity<>(statusService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{title}") // Поиск статуса по названию
    public ResponseEntity<Status> findByTitle(@PathVariable String title) throws StatusNotFoundException {
        return new ResponseEntity<>(statusService.findByName(title), HttpStatus.OK);
    }
}
