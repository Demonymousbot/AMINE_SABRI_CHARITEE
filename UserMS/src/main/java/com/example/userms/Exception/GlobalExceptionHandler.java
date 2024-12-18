package com.example.userms.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handle(UserNotFoundException exp){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND).body(exp.getMsg());
    }

}
//41MIN JUS
/*@ExceptionHandler(UserNotFoundException.class)
public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exp){
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND).body(exp.getMsg());
}*/


