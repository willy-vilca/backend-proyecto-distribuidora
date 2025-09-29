package com.distribuidora.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderResponse {
    private Integer orderId;
    private String message;
}
