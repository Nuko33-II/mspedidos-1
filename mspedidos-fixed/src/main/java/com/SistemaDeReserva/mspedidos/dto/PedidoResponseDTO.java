package com.SistemaDeReserva.mspedidos.dto;
import lombok.*;
import java.math.BigDecimal;
/**
 * DTO (Data Transfer Object) - PedidoResponseDTO.java
 * 
 * Descripcion: Objeto de transferencia de datos (DTO) para la comunicación del microservicio mspedidos.
 * 
 * @author Sistema de Reservas de Restaurante
 * @version 1.0.0
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PedidoResponseDTO {
    private Long idPedido,idReserva,idPlato;
    private Integer cantidad;
    private BigDecimal precioUnitario,total;
    private String estado;
}
