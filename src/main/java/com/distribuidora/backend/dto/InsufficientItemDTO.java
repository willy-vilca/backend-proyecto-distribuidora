package com.distribuidora.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsufficientItemDTO {
    private Integer productId;
    private String nombre;
    private Integer stockActual;
    private Integer solicitado;
}
