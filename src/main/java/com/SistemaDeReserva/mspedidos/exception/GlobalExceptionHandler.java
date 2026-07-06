package com.SistemaDeReserva.mspedidos.exception;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Manejador Global de Excepciones - GlobalExceptionHandler.java
 * 
 * Descripcion: Manejador centralizado de excepciones para capturar y estandarizar respuestas de error en mspedidos.
 * 
 * @author Sistema de Reservas de Restaurante
 * @version 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Ejecuta un proceso dentro de la capa arquitectonica.
     */
    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(PedidoNotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .mensaje(e.getMessage())
                .path(request.getRequestURI())
                .build());
    }

    /**
     * Ejecuta un proceso dentro de la capa arquitectonica.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, String> campos = new HashMap<>();
                e.getBindingResult().getAllErrors().forEach(er -> {
            if (er instanceof FieldError fieldError) {
                campos.put(fieldError.getField(), fieldError.getDefaultMessage());
            } else {
                campos.put(er.getObjectName(), er.getDefaultMessage());
            }
        });
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Failed")
                .mensaje(campos.toString())
                .path(request.getRequestURI())
                .build());
    }

    /**
     * Ejecuta un proceso dentro de la capa arquitectonica.
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> illegalArgument(IllegalArgumentException e, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Failed")
                .mensaje(e.getMessage())
                .path(request.getRequestURI())
                .build());
    }

    /**
     * Ejecuta un proceso dentro de la capa arquitectonica.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> illegalState(IllegalStateException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("Business Rule Violation")
                .mensaje(e.getMessage())
                .path(request.getRequestURI())
                .build());
    }

    /**
     * Ejecuta un proceso dentro de la capa arquitectonica.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> general(Exception e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .mensaje("Error inesperado en el servidor")
                .path(request.getRequestURI())
                .build());
    }
}
