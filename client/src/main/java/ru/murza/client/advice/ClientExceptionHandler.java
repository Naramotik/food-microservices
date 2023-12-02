package ru.murza.client.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.murza.client.exception.ClientNotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ClientExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClientNotFoundException.class)
    public Map<String, String> handleClientException(ClientNotFoundException ex){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage: ", ex.getMessage());
        return errorMap;
    }

}
