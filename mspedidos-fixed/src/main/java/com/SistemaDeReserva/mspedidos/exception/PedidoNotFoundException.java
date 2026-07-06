package com.SistemaDeReserva.mspedidos.exception;
/**
 * Entidad de Dominio - PedidoNotFoundException.java
 * 
 * Descripcion: Entidad JPA que mapea la tabla de base de datos para la gestión del dominio Pedidos.
 * 
 * @author Sistema de Reservas de Restaurante
 * @version 1.0.0
 */
public class PedidoNotFoundException extends RuntimeException {
    /**
     * Ejecuta un proceso dentro de la capa arquitectonica.
     */
    public PedidoNotFoundException(Long id){super("Pedido con ID "+id+" no encontrado");}
}
