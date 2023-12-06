package ru.murza.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Status")
public class StatusController {
    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @Operation(
            summary = "Создание нового статуса",
            description = "Создание нового статуса",
            responses = @ApiResponse(description = "CREATED", responseCode = "201")
    )
    @PostMapping
    public ResponseEntity<Status> save(@Valid @RequestBody Status status){
        return new ResponseEntity<>(statusService.save(status), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Вывод всех статусов",
            description = "Вывод всех статусов",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping
    public ResponseEntity<List<Status>> getAll(){
        return new ResponseEntity<>(statusService.getAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Поиск статуса по названию",
            description = "Поиск статуса по названию",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping("/{title}")
    public ResponseEntity<Status> findByTitle(@PathVariable String title) throws StatusNotFoundException {
        return new ResponseEntity<>(statusService.findByName(title), HttpStatus.OK);
    }
}
