package com.distribuidora.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoUsuarioDTO {
    private Integer idPedido;
    private LocalDate fechaPedido;
    private BigDecimal total;
}