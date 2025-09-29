package com.distribuidora.backend.controller;

import com.distribuidora.backend.dto.*;
import com.distribuidora.backend.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoService pedidoService;

    // Check stock
    @PostMapping("/check-stock")
    public ResponseEntity<StockCheckResponse> checkStock(@RequestBody List<ItemRequest> items) {
        StockCheckResponse resp = pedidoService.checkStock(items);
        if (!resp.isOk()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        }
        return ResponseEntity.ok(resp);
    }

    // Crear pedido
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest req) {
        try {
            CreateOrderResponse resp = pedidoService.createOrder(req);
            return ResponseEntity.status(HttpStatus.CREATED).body(resp);
        } catch (IllegalStateException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error creando pedido"));
        }
    }
}
