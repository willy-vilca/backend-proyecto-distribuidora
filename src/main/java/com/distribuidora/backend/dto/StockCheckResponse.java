package com.distribuidora.backend.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockCheckResponse {
    private boolean ok;
    private List<InsufficientItemDTO> insuficientes;
}
