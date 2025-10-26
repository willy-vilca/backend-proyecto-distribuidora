package com.distribuidora.backend.repository;

import com.distribuidora.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo AND u.id <> :idUsuario")
    Optional<Usuario> findByCorreoAndNotId(String correo, Integer idUsuario);
}
