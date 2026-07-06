package com.SistemaDeReserva.mspedidos.exception;

import lombok.*;
import java.time.LocalDateTime;

/**
 * Entidad de Dominio - ErrorResponse.java
 * 
 * Descripcion: Entidad JPA que mapea la tabla de base de datos para la gestión del dominio Pedidos.
 * 
 * @author Sistema de Reservas de Restaurante
 * @version 1.0.0
 */
@Data @Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String mensaje;
    private String path;
}
