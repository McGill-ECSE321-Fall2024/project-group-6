package ca.mcgill.ecse321.gameshop.exception;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ca.mcgill.ecse321.gameshop.dto.ErrorDto;

@ControllerAdvice
public class GameShopExceptionHandler {
    @ExceptionHandler(GameShopException.class)
    public ResponseEntity<ErrorDto> handleGameShopException(GameShopException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), e.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ArrayList<String> errorMessages = new ArrayList<>();
        for (ObjectError err : ex.getAllErrors()) {
            errorMessages.add(err.getDefaultMessage());
        }
        ErrorDto responseBody = new ErrorDto(errorMessages);
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
}