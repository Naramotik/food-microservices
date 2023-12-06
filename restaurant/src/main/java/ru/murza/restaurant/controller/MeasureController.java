package ru.murza.restaurant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.murza.foodmodel.models.Measure;
import ru.murza.restaurant.dto.MeasureDTO;
import ru.murza.restaurant.exception.MeasureNotFoundException;
import ru.murza.restaurant.service.MeasureService;
import ru.murza.restaurant.util.Mapper;


import java.util.List;

@RestController
@Transactional
@RequestMapping("/api/v1/measure")
@Tag(name = "Measure")
public class MeasureController {

    private final MeasureService measureService;

    public MeasureController(MeasureService measureService) {
        this.measureService = measureService;
    }

    @Operation(
            summary = "Вывод всех мер",
            description = "Вывод всех мер",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping
    public ResponseEntity<List<MeasureDTO>> getAllMeasures(){
        List<MeasureDTO> measureDTOList = measureService.getMeasures()
                .stream()
                .map(measure -> Mapper.modelMapper.map(measure, MeasureDTO.class))
                .toList();
        return new ResponseEntity<>(measureDTOList, HttpStatus.OK);
    }

    @Operation(
            summary = "Добавление новой меры",
            description = "Добавление новой меры",
            responses = @ApiResponse(description = "CREATED", responseCode = "201")
    )
    @PostMapping
    public ResponseEntity<MeasureDTO> addMeasure(@Valid @RequestBody Measure measure){
        MeasureDTO outMeasureDTO = Mapper.modelMapper.map(measureService.save(measure), MeasureDTO.class);
        return new ResponseEntity<>(outMeasureDTO, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Изменение меры",
            description = "Изменение меры",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @PutMapping("/{measureId}")
    public ResponseEntity<MeasureDTO> putMeasure(@PathVariable("measureId") Long measureId,
                                                 @RequestBody MeasureDTO measureDTO) throws MeasureNotFoundException {
        Measure measure = Mapper.modelMapper.map(measureDTO, Measure.class);
        MeasureDTO outMeasureDTO = Mapper.modelMapper.map(measureService.putMeasure(measureId, measure), MeasureDTO.class);
        return new ResponseEntity<>(outMeasureDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Удаление меры",
            description = "Удаление меры",
            responses = @ApiResponse(description = "NO_CONTENT", responseCode = "204")
    )
    @DeleteMapping("/{measureId}")
    public ResponseEntity<?> deleteMeasure (@PathVariable("measureId") Long measureId){
        measureService.deleteById(measureId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
