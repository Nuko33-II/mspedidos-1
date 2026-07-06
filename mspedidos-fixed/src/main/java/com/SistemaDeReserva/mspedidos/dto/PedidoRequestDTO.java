package com.SistemaDeReserva.mspedidos.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
/**
 * DTO (Data Transfer Object) - PedidoRequestDTO.java
 * 
 * Descripcion: Objeto de transferencia de datos (DTO) para la comunicación del microservicio mspedidos.
 * 
 * @author Sistema de Reservas de Restaurante
 * @version 1.0.0
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PedidoRequestDTO {
    @NotNull private Long idReserva;
    @NotNull private Long idPlato;
    @NotNull @Min(1) private Integer cantidad;
    @NotNull @DecimalMin("0.0") private BigDecimal precioUnitario;
}
