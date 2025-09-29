package com.distribuidora.backend.repository;

import com.distribuidora.backend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    // Métodos básicos vienen por defecto (findAll, findById, save, etc.)
}
