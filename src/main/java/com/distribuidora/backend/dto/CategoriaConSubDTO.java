package com.distribuidora.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaConSubDTO {
    private Integer id;
    private String nombre;
    private List<SubcategoriaDTO> subcategorias;
}
