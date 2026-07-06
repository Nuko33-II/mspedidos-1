package com.SistemaDeReserva.mspedidos.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
/**
 * Entidad de Dominio - Pedido.java
 * 
 * Descripcion: Entidad JPA que mapea la tabla de base de datos para la gestión del dominio Pedidos.
 * 
 * @author Sistema de Reservas de Restaurante
 * @version 1.0.0
 */
@Entity @Table(name="pedidos")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Pedido {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_pedido") private Long idPedido;
    @NotNull @Column(name="id_reserva",nullable=false) private Long idReserva;
    @NotNull @Column(name="id_plato",nullable=false) private Long idPlato;
    @NotNull @Min(1) @Column(nullable=false) private Integer cantidad;
    @NotNull @DecimalMin("0.0") @Column(name="precio_unitario",nullable=false,precision=10,scale=2) private BigDecimal precioUnitario;
    @Column(nullable=false,length=30) @Builder.Default private String estado="PENDIENTE";
}
