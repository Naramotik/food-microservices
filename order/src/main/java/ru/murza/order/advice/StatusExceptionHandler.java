package ru.murza.order.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.murza.order.exception.StatusNotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class StatusExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(StatusNotFoundException.class)
    public Map<String, String> handleStatusException(StatusNotFoundException ex){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage: ", ex.getMessage());
        return errorMap;
    }

}
