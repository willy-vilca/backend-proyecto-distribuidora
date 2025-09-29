package com.distribuidora.backend.service;

import com.distribuidora.backend.dto.SubcategoriaDTO;
import com.distribuidora.backend.model.Subcategoria;

import java.util.List;

public interface SubcategoriaService {
    List<Subcategoria> getSubcategoriasByCategoriaId(Integer categoriaId);
    SubcategoriaDTO getSubcategoriaConCategoriaPadre(Integer subcategoriaId);
}