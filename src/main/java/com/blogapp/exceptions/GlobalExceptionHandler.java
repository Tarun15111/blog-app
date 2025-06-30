package com.blogapp.exceptions;

import com.blogapp.payloads.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception){
        String message = exception.getMessage();
        APIResponse apiResponse = new APIResponse(message, false);
        return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

//
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<Map<String, String>> handleMethodsArgsNotValidException(MethodArgumentNotValidException exception){
        Map<String, String> resp = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error ->{
            logger.info(String.valueOf(error));
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            resp.put(fieldName, message);
        });
        return  new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

}
