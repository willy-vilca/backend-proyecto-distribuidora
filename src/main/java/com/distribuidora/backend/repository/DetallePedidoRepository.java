package com.distribuidora.backend.repository;

import com.distribuidora.backend.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
    @Query("SELECT dp FROM DetallePedido dp JOIN FETCH dp.producto p WHERE dp.pedido.id = :pedidoId")
    List<DetallePedido> findByPedidoIdFetchProducto(@Param("pedidoId") Integer pedidoId);
}
