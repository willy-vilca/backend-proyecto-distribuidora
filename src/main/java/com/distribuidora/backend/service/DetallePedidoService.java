package com.distribuidora.backend.service;

import com.distribuidora.backend.dto.DetallePedidoDTO;
import java.util.List;

public interface DetallePedidoService {
    List<DetallePedidoDTO> getDetallesByPedido(Integer pedidoId);
}
