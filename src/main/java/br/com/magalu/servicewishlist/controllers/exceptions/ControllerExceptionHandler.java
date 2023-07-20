package br.com.magalu.servicewishlist.controllers.exceptions;

import br.com.magalu.servicewishlist.servicies.exceptions.ClientNotFoundException;
import br.com.magalu.servicewishlist.servicies.exceptions.ProductNotFoundException;
import br.com.magalu.servicewishlist.servicies.exceptions.WishlistExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseStatusExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ClientNotFoundException e) {
        StandardError error = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ProductNotFoundException e) {
        StandardError error = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(WishlistExceededException.class)
    public ResponseEntity<StandardError> objectNotFound(WishlistExceededException e) {
        StandardError error = new StandardError(HttpStatus.CONFLICT.value(), e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
