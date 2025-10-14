package com.distribuidora.backend.repository;

import com.distribuidora.backend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    @Query("SELECT DISTINCT c FROM Categoria c LEFT JOIN FETCH c.subcategorias s")
    List<Categoria> findAllWithSubcategorias();
}
