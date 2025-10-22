package com.distribuidora.backend.repository;

import com.distribuidora.backend.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByIdUsuarioOrderByFechaPedidoDesc(Integer idUsuario);
}
