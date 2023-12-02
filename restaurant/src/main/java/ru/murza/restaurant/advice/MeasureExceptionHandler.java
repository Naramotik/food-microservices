package ru.murza.restaurant.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.murza.restaurant.exception.MeasureNotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MeasureExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MeasureNotFoundException.class)
    public Map<String, String> handleMeasureException(MeasureNotFoundException ex){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage: ", ex.getMessage());
        return errorMap;
    }

}
