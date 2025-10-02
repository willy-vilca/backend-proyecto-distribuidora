package com.distribuidora.backend.repository;

import com.distribuidora.backend.model.Producto;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query("SELECT p FROM Producto p JOIN FETCH p.subcategoria s WHERE s.id = :subcategoriaId")
    List<Producto> findBySubcategoriaIdFetchJoin(@Param("subcategoriaId") Integer subcategoriaId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Producto p WHERE p.id IN :ids")
    List<Producto> findAllByIdInForUpdate(@Param("ids") List<Integer> ids);

    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%'))")
    List<Producto> findByNombreContainingIgnoreCase(@Param("busqueda") String busqueda);
}
