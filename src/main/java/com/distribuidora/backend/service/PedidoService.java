package com.distribuidora.backend.service;

import com.distribuidora.backend.dto.*;
import java.util.List;

public interface PedidoService {
    StockCheckResponse checkStock(List<ItemRequest> items);
    CreateOrderResponse createOrder(CreateOrderRequest req);
    List<PedidoUsuarioDTO> getPedidosByUsuarioId(Integer idUsuario);
}
