package com.distribuidora.backend.repository;

import com.distribuidora.backend.model.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Integer> {
    // Encuentra subcategorías por el id de la categoría padre
    List<Subcategoria> findByCategoriaPadre_Id(Integer categoriaId);
}