package com.distribuidora.backend.service;

import com.distribuidora.backend.dto.ProductoDTO;

import java.util.List;

public interface ProductoService {
    List<ProductoDTO> getProductosBySubcategoria(Integer subcategoriaId);
    ProductoDTO getProductoById(Integer id);
    List<ProductoDTO> buscarProductosPorNombre(String busqueda);
}

