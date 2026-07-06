package com.SistemaDeReserva.mspedidos.repository;
import com.SistemaDeReserva.mspedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
/**
 * Repositorio JPA - PedidoRepository.java
 * 
 * Descripcion: Interfaz de repositorio JPA para acceso a datos y persistencia de la entidad Pedidos.
 * 
 * @author Sistema de Reservas de Restaurante
 * @version 1.0.0
 */
public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    List<Pedido> findByIdReserva(Long idReserva);
    List<Pedido> findByEstado(String estado);
}
