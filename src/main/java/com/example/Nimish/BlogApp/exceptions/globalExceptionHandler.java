package com.example.Nimish.BlogApp.exceptions;

import com.example.Nimish.BlogApp.payloads.apiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class globalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<apiResponse> resourceNotFoundExceptionHandler( ResourceNotFoundException ex){
        String message = ex.getMessage();
        apiResponse apiResponse = new apiResponse(message,false);
        return new ResponseEntity<apiResponse>(apiResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            resp.put(fieldName,message);
        });

        return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<apiResponse> illegalArgumentExceptionHandler (IllegalArgumentException  ex){
        String message = ex.getMessage();
        apiResponse apiResponse =new apiResponse(message,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

}
