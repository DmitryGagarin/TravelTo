package com.travel.to.travel_to.exception;

import com.sun.jdi.InvocationException;
import com.travel.to.travel_to.exception.exception.FileExtensionException;
import com.travel.to.travel_to.exception.exception.InvalidEmailOrPasswordException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, String>> handleBindException(BindException ex) {
        Map<String, String> errorMessages = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
    error -> errorMessages.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errorMessages);
    }

    // 401
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidEmailOrPasswordException.class)
    public ResponseEntity<Map<String, String>> handleInvalidEmailOrPasswordException(InvalidEmailOrPasswordException ex) {
        Map<String, String> errorMessages = new HashMap<>();
        errorMessages.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(errorMessages);
    }

    // 415
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(FileExtensionException.class)
    public ResponseEntity<Map<String, String>> handleFileExtensionException(FileExtensionException ex) {
        Map<String, String> errorMessages = new HashMap<>();
        errorMessages.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(errorMessages);
    }

    // 503
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler({
        InvocationException.class,
        NullPointerException.class,
        IllegalArgumentException.class,
        IllegalStateException.class,
        ArrayIndexOutOfBoundsException.class,
        StringIndexOutOfBoundsException.class,
        NumberFormatException.class,
    })
    public ResponseEntity<Map<String, String>> handleCommonException(
        RuntimeException ex
    ) {
        Map<String, String> errorMessages = new HashMap<>();
        errorMessages.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(errorMessages);
    }
}
