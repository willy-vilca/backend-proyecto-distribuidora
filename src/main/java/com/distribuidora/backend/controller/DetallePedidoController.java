package com.distribuidora.backend.controller;

import com.distribuidora.backend.dto.DetallePedidoDTO;
import com.distribuidora.backend.service.DetallePedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/detalle-pedidos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DetallePedidoController {

    private final DetallePedidoService detallePedidoService;


    @GetMapping("/pedido/{pedidoId}")
    public List<DetallePedidoDTO> obtenerDetallesPorPedido(@PathVariable Integer pedidoId) {
        return detallePedidoService.getDetallesByPedido(pedidoId);
    }
}
