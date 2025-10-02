package com.distribuidora.backend.service;

import com.distribuidora.backend.dto.*;
import com.distribuidora.backend.model.*;
import com.distribuidora.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final ProductoRepository productoRepository;
    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;

    @Override
    public StockCheckResponse checkStock(List<ItemRequest> items) {
        if (items == null || items.isEmpty()) {
            return new StockCheckResponse(true, Collections.emptyList());
        }

        List<Integer> ids = items.stream()
                .map(ItemRequest::getProductId)
                .distinct()
                .collect(Collectors.toList());

        List<Producto> productos = productoRepository.findAllById(ids); // read-only

        Map<Integer, Producto> map = productos.stream().collect(Collectors.toMap(Producto::getId, p -> p));

        List<InsufficientItemDTO> insuf = new ArrayList<>();
        for (ItemRequest it : items) {
            Producto p = map.get(it.getProductId());
            if (p == null) {
                insuf.add(new InsufficientItemDTO(it.getProductId(), "Producto no encontrado", 0, it.getCantidad()));
            } else {
                int stock = p.getStock() == null ? 0 : p.getStock();
                if (stock < it.getCantidad()) {
                    insuf.add(new InsufficientItemDTO(p.getId(), p.getNombre(), stock, it.getCantidad()));
                }
            }
        }

        return new StockCheckResponse(insuf.isEmpty(), insuf);
    }

    @Override
    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest req) {
        List<ItemRequest> items = req.getItems();
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("No hay items en el pedido");
        }

        List<Integer> ids = items.stream().map(ItemRequest::getProductId).distinct().collect(Collectors.toList());
        List<Producto> productos = productoRepository.findAllByIdInForUpdate(ids);
        Map<Integer, Producto> map = productos.stream().collect(Collectors.toMap(Producto::getId, p -> p));

        // revalidar stock
        for (ItemRequest it : items) {
            Producto p = map.get(it.getProductId());
            if (p == null) {
                throw new IllegalStateException("Producto no encontrado: " + it.getProductId());
            }
            int stock = p.getStock() == null ? 0 : p.getStock();
            if (stock < it.getCantidad()) {
                throw new IllegalStateException("Stock insuficiente para producto: " + p.getNombre());
            }
        }

        // calcular totales y crear pedido
        BigDecimal total = BigDecimal.ZERO;
        for (ItemRequest it : items) {
            Producto p = map.get(it.getProductId());
            BigDecimal precio = p.getPrecio() == null ? BigDecimal.ZERO : p.getPrecio();
            BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(it.getCantidad()));
            total = total.add(subtotal);
        }

        Pedido pedido = new Pedido();
        pedido.setIdCliente(null);
        pedido.setIdUsuario(req.getUsuarioId());
        pedido.setFechaPedido(LocalDate.now());
        pedido.setTotal(req.getTotal() != null ? req.getTotal() : total);
        pedido.setIdRuta(null);
        pedido.setDireccion(req.getDireccion());

        Pedido saved = pedidoRepository.save(pedido);

        // Guardar DetallePedido y actualizar stock
        List<DetallePedido> detalles = new ArrayList<>();
        for (ItemRequest it : items) {
            Producto p = map.get(it.getProductId());
            BigDecimal precio = p.getPrecio() == null ? BigDecimal.ZERO : p.getPrecio();
            BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(it.getCantidad()));

            DetallePedido dp = new DetallePedido();
            dp.setPedido(saved);
            dp.setProducto(p);
            dp.setCantidad(it.getCantidad());
            dp.setSubtotal(subtotal);

            detalles.add(dp);

            int nuevoStock = (p.getStock() == null ? 0 : p.getStock()) - it.getCantidad();
            p.setStock(nuevoStock);
        }

        detallePedidoRepository.saveAll(detalles);
        productoRepository.saveAll(productos);

        return new CreateOrderResponse(saved.getId(), "Pedido creado con éxito");
    }
}
