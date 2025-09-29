package com.distribuidora.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    private Integer productId;
    private Integer cantidad;
}
