package com.distribuidora.backend.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallePedidoDTO {
    private Integer idProducto;
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal subtotal;
}
