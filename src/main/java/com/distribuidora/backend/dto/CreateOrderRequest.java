package com.distribuidora.backend.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    private Integer usuarioId;
    private String direccion;
    private List<ItemRequest> items;
    private BigDecimal total;
    private String paymentId;
}
