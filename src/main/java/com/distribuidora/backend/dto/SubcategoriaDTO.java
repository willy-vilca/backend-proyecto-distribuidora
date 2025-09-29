package com.distribuidora.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubcategoriaDTO {
    private Integer id;
    private String nombre;
    private Integer idCategoriaPadre;
    private String nombreCategoriaPadre;
}
