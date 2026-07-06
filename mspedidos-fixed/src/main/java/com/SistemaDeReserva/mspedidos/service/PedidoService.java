package com.SistemaDeReserva.mspedidos.service;
import com.SistemaDeReserva.mspedidos.dto.*;
import com.SistemaDeReserva.mspedidos.exception.PedidoNotFoundException;
import com.SistemaDeReserva.mspedidos.model.Pedido;
import com.SistemaDeReserva.mspedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Servicio de Negocio - PedidoService.java
 * 
 * Descripcion: Clase de servicio que encapsula la lógica de negocio para la gestión de mspedidos.
 * 
 * @author Sistema de Reservas de Restaurante
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repo;

    private static final Set<String> ESTADOS_VALIDOS = Set.of(
            "PENDIENTE", "EN_PREPARACION", "LISTO", "ENTREGADO", "CANCELADO");

    /**
     * Obtiene todos los registros existentes en el microservicio mspedidos.
     * @return ResponseEntity con la lista de elementos
     */
    public List<PedidoResponseDTO> obtenerTodos() {
        log.info("Obteniendo todos los pedidos");
        List<PedidoResponseDTO> result = repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
        log.info("Total pedidos encontrados: {}", result.size());
        return result;
    }

    /**
     * Busca y retorna un registro especifico por su identificador unico.
     * @param id identificador unico del registro
     * @return ResponseEntity con los detalles del registro
     */
    public PedidoResponseDTO obtenerPorId(Long id) {
        log.info("Obteniendo pedido por ID: {}", id);
        PedidoResponseDTO result = toDTO(repo.findById(id).orElseThrow(() -> new PedidoNotFoundException(id)));
        log.info("Pedido encontrado con ID: {}", id);
        return result;
    }

    /**
     * Ejecuta un proceso dentro de la capa arquitectonica.
     */
    public List<PedidoResponseDTO> porReserva(Long idReserva) {
        log.info("Obteniendo pedidos por reserva ID: {}", idReserva);
        List<PedidoResponseDTO> result = repo.findByIdReserva(idReserva).stream().map(this::toDTO).collect(Collectors.toList());
        log.info("Total pedidos para reserva {}: {}", idReserva, result.size());
        return result;
    }

    /**
     * Registra y crea un nuevo elemento en la base de datos.
     * @param dto datos de creacion encapsulados en el DTO
     * @return ResponseEntity con el registro creado
     */
    public PedidoResponseDTO crear(PedidoRequestDTO dto) {
        log.info("Creando pedido: {}", dto);
        Pedido p = Pedido.builder()
                .idReserva(dto.getIdReserva())
                .idPlato(dto.getIdPlato())
                .cantidad(dto.getCantidad())
                .precioUnitario(dto.getPrecioUnitario())
                .estado("PENDIENTE")
                .build();
        PedidoResponseDTO result = toDTO(repo.save(p));
        log.info("Pedido creado con ID: {}", result.getIdPedido());
        return result;
    }

    /**
     * Ejecuta un proceso dentro de la capa arquitectonica.
     */
    public PedidoResponseDTO cambiarEstado(Long id, String estado) {
        log.info("Cambiando estado pedido ID: {} a {}", id, estado);
        if (!ESTADOS_VALIDOS.contains(estado))
            throw new IllegalArgumentException("Estado invalido: " + estado + ". Valores permitidos: " + ESTADOS_VALIDOS);
        Pedido p = repo.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
        p.setEstado(estado);
        PedidoResponseDTO result = toDTO(repo.save(p));
        log.info("Estado del pedido ID: {} cambiado a {}", id, estado);
        return result;
    }

    /**
     * Actualiza un registro existente identificado por su ID.
     * @param id identificador del elemento
     * @param dto nuevos datos del registro
     * @return ResponseEntity con el registro actualizado
     */
    public PedidoResponseDTO actualizar(Long id, PedidoRequestDTO dto) {
        log.info("Actualizando pedido ID: {}", id);
        Pedido p = repo.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
        if (!"PENDIENTE".equals(p.getEstado()))
            throw new IllegalStateException("Solo se pueden modificar pedidos en estado PENDIENTE");
        p.setIdPlato(dto.getIdPlato());
        p.setCantidad(dto.getCantidad());
        p.setPrecioUnitario(dto.getPrecioUnitario());
        PedidoResponseDTO result = toDTO(repo.save(p));
        log.info("Pedido ID: {} actualizado correctamente", id);
        return result;
    }

    /**
     * Elimina o desactiva logicamente un registro por su identificador unico.
     * @param id identificador unico a eliminar
     * @return ResponseEntity indicando el exito de la operacion (No Content)
     */
    public void eliminar(Long id) {
        log.info("Eliminando pedido ID: {}", id);
        repo.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
        repo.deleteById(id);
        log.info("Pedido ID: {} eliminado correctamente", id);
    }

    /**
     * Ejecuta un proceso dentro de la capa arquitectonica.
     */
    private PedidoResponseDTO toDTO(Pedido p) {
        return PedidoResponseDTO.builder()
                .idPedido(p.getIdPedido())
                .idReserva(p.getIdReserva())
                .idPlato(p.getIdPlato())
                .cantidad(p.getCantidad())
                .precioUnitario(p.getPrecioUnitario())
                .total(p.getPrecioUnitario().multiply(java.math.BigDecimal.valueOf(p.getCantidad())))
                .estado(p.getEstado())
                .build();
    }
}
