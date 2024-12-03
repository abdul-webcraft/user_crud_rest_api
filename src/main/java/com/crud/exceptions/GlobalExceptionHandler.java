package com.crud.exceptions;

import com.crud.payloads.response.ApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorMessage = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errorMessage.add(error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(new ApiExceptionResponse("FAIL",errorMessage.toString()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> resourceNotFoundExceptionHandler(UserNotFoundException ex){
        String message = ex.getMessage();
        ApiExceptionResponse apiResponse=new ApiExceptionResponse("FAIL",message);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiExceptionResponse> resourceNotFoundExceptionHandler(UserAlreadyExistsException ex){
        String message = ex.getMessage();
        ApiExceptionResponse apiResponse=new ApiExceptionResponse("FAIL",message);
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserServiceLogicException.class)
    public ResponseEntity<ApiExceptionResponse> resourceNotFoundExceptionHandler(UserServiceLogicException ex){
        String message = ex.getMessage();
        ApiExceptionResponse apiResponse=new ApiExceptionResponse("FAIL","An unexpected error occurred !!");
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
