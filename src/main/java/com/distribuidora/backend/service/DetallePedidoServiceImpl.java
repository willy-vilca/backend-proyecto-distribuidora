package com.distribuidora.backend.service;

import com.distribuidora.backend.dto.DetallePedidoDTO;
import com.distribuidora.backend.model.DetallePedido;
import com.distribuidora.backend.repository.DetallePedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;

    public DetallePedidoServiceImpl(DetallePedidoRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetallePedidoDTO> getDetallesByPedido(Integer pedidoId) {
        List<DetallePedido> detalles = detallePedidoRepository.findByPedidoIdFetchProducto(pedidoId);

        return detalles.stream().map(dp -> DetallePedidoDTO.builder()
                .idProducto(dp.getProducto().getId())
                .nombreProducto(dp.getProducto().getNombre())
                .cantidad(dp.getCantidad())
                .subtotal(dp.getSubtotal())
                .build()
        ).collect(Collectors.toList());
    }
}
