package com.SistemaDeReserva.mspedidos.service;

import com.SistemaDeReserva.mspedidos.dto.PedidoRequestDTO;
import com.SistemaDeReserva.mspedidos.dto.PedidoResponseDTO;
import com.SistemaDeReserva.mspedidos.exception.PedidoNotFoundException;
import com.SistemaDeReserva.mspedidos.model.Pedido;
import com.SistemaDeReserva.mspedidos.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository repo;

    @InjectMocks
    private PedidoService service;

    @Test
    public void testObtenerTodos() {
        Pedido p = Pedido.builder()
                .idPedido(1L)
                .idReserva(10L)
                .idPlato(20L)
                .cantidad(2)
                .precioUnitario(new BigDecimal("15.00"))
                .estado("PENDIENTE")
                .build();

        when(repo.findAll()).thenReturn(List.of(p));

        List<PedidoResponseDTO> res = service.obtenerTodos();

        assertNotNull(res);
        assertEquals(1, res.size());
        assertEquals(2, res.get(0).getCantidad());
        assertEquals(new BigDecimal("30.00"), res.get(0).getTotal());
    }

    @Test
    public void testObtenerPorIdExitoso() {
        Pedido p = Pedido.builder()
                .idPedido(1L)
                .idReserva(10L)
                .idPlato(20L)
                .cantidad(2)
                .precioUnitario(new BigDecimal("15.00"))
                .estado("PENDIENTE")
                .build();

        when(repo.findById(1L)).thenReturn(Optional.of(p));

        PedidoResponseDTO res = service.obtenerPorId(1L);

        assertNotNull(res);
        assertEquals(1L, res.getIdPedido());
    }

    @Test
    public void testObtenerPorIdNoEncontrado() {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PedidoNotFoundException.class, () -> {
            service.obtenerPorId(1L);
        });
    }

    @Test
    public void testCrearExitoso() {
        PedidoRequestDTO request = PedidoRequestDTO.builder()
                .idReserva(10L)
                .idPlato(20L)
                .cantidad(2)
                .precioUnitario(new BigDecimal("15.00"))
                .build();

        Pedido guardado = Pedido.builder()
                .idPedido(1L)
                .idReserva(10L)
                .idPlato(20L)
                .cantidad(2)
                .precioUnitario(new BigDecimal("15.00"))
                .estado("PENDIENTE")
                .build();

        when(repo.save(any(Pedido.class))).thenReturn(guardado);

        PedidoResponseDTO res = service.crear(request);

        assertNotNull(res);
        assertEquals(1L, res.getIdPedido());
        assertEquals("PENDIENTE", res.getEstado());
    }
}
