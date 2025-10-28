package org.unibl.etf.ip.etfbl_ipbackend.exc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();

        if (!errors.isEmpty()) {
            String message = errors.get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(message);
        }

        return ResponseEntity.badRequest().body("Invalid request");
    }
}

