package com.SistemaDeReserva.mspedidos.controller;
import com.SistemaDeReserva.mspedidos.dto.*;
import com.SistemaDeReserva.mspedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST - PedidoController.java
 * 
 * Descripcion: Controlador REST para el microservicio de mspedidos. Proporciona endpoints para la API pública.
 * 
 * @author Sistema de Reservas de Restaurante
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService svc;

    /**
     * Obtiene todos los registros existentes en el microservicio mspedidos.
     * @return ResponseEntity con la lista de elementos
     */
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> todos() {
        log.info("GET /api/v1/pedidos");
        return ResponseEntity.ok(svc.obtenerTodos());
    }

    /**
     * Busca y retorna un registro especifico por su identificador unico.
     * @param id identificador unico del registro
     * @return ResponseEntity con los detalles del registro
     */
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> porId(@PathVariable Long id) {
        log.info("GET /api/v1/pedidos/{}", id);
        return ResponseEntity.ok(svc.obtenerPorId(id));
    }

    /**
     * Ejecuta un proceso dentro de la capa arquitectonica.
     */
    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<List<PedidoResponseDTO>> porReserva(@PathVariable Long idReserva) {
        log.info("GET /api/v1/pedidos/reserva/{}", idReserva);
        return ResponseEntity.ok(svc.porReserva(idReserva));
    }

    /**
     * Registra y crea un nuevo elemento en la base de datos.
     * @param dto datos de creacion encapsulados en el DTO
     * @return ResponseEntity con el registro creado
     */
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crear(@Valid @RequestBody PedidoRequestDTO dto) {
        log.info("POST /api/v1/pedidos");
        return ResponseEntity.status(HttpStatus.CREATED).body(svc.crear(dto));
    }

    /**
     * Actualiza un registro existente identificado por su ID.
     * @param id identificador del elemento
     * @param dto nuevos datos del registro
     * @return ResponseEntity con el registro actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PedidoRequestDTO dto) {
        log.info("PUT /api/v1/pedidos/{}", id);
        return ResponseEntity.ok(svc.actualizar(id, dto));
    }

    /**
     * Realiza una modificacion parcial sobre un atributo especifico del registro.
     * @param id identificador del registro
     * @return ResponseEntity con el registro modificado
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoResponseDTO> estado(@PathVariable Long id, @RequestParam String estado) {
        log.info("PATCH /api/v1/pedidos/{}/estado", id);
        return ResponseEntity.ok(svc.cambiarEstado(id, estado));
    }

    /**
     * Elimina o desactiva logicamente un registro por su identificador unico.
     * @param id identificador unico a eliminar
     * @return ResponseEntity indicando el exito de la operacion (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/v1/pedidos/{}", id);
        svc.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
