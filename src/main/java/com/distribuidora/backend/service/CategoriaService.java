package com.distribuidora.backend.service;

import com.distribuidora.backend.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    List<Categoria> getAllCategorias();
    Optional<Categoria> getCategoriaById(Integer id);
}