package cl.cardenas.controllers;

import cl.cardenas.controllers.entities.ErrorDetailsResponse;
import cl.cardenas.exceptions.EmailNotMatchException;
import cl.cardenas.exceptions.InvalidPasswordException;
import cl.cardenas.exceptions.UserAlreadyExistException;
import cl.cardenas.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler({UserAlreadyExistException.class})
    public ResponseEntity<ErrorDetailsResponse> userAlreadyExistException() {
        var errorDetails = ErrorDetailsResponse
                .builder()
                .message("¡Email ya existe!")
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorDetailsResponse> userNotFoundException() {
        var errorDetails = ErrorDetailsResponse
                .builder()
                .message("¡El usuario no existe!")
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EmailNotMatchException.class})
    public ResponseEntity<ErrorDetailsResponse> emailNotMatchException() {
        var errorDetails = ErrorDetailsResponse
                .builder()
                .message("¡Email invalido!")
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidPasswordException.class})
    public ResponseEntity<ErrorDetailsResponse> invalidPasswordException() {
        var errorDetails = ErrorDetailsResponse
                .builder()
                .message("¡Password invalido!")
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
