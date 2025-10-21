package com.distribuidora.backend.service;

import com.distribuidora.backend.dto.AuthResponse;
import com.distribuidora.backend.dto.LoginRequest;
import com.distribuidora.backend.dto.RegisterRequest;
import com.distribuidora.backend.model.Usuario;
import com.distribuidora.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest req) {
        if (usuarioRepository.existsByCorreo(req.getCorreo())) {
            return new AuthResponse(null, null, null,null,null, "El correo ya se encuentra registrado en otra cuenta, intente con otro o inicie sesión.", null);
        }

        Usuario u = Usuario.builder()
                .correo(req.getCorreo().toLowerCase())
                .nombre(req.getNombre())
                .telefono(req.getTelefono())
                .contrasena(passwordEncoder.encode(req.getContrasena()))
                .fechaRegistro(LocalDateTime.now())
                .dni(req.getDni())
                .build();

        usuarioRepository.save(u);
        return new AuthResponse(u.getId(), u.getCorreo(), u.getNombre(), u.getTelefono(),u.getDni(), "Usuario registrado correctamente", null);
    }

    @Override
    public AuthResponse login(LoginRequest req) {
        return usuarioRepository.findByCorreo(req.getCorreo().toLowerCase())
                .map(u -> {
                    if (passwordEncoder.matches(req.getContrasena(), u.getContrasena())) {
                        return new AuthResponse(u.getId(), u.getCorreo(), u.getNombre(), u.getTelefono(),u.getDni(), "Login correcto", null);
                    } else {
                        return new AuthResponse(null, null, null,null,null, "Credenciales inválidas, la contraseña es incorrecta, intente nuevamente por favor.", null);
                    }
                }).orElseGet(() -> new AuthResponse(null, null, null, null,null, "Usuario no encontrado, el correo que ingreso no está asociada a ninguna cuenta registrada", null));
    }

    @Override
    public AuthResponse loginOrRegisterGoogle(String correo, String nombre) {
        // Busca por correo; si no existe crea uno (clave aleatoria)
        return usuarioRepository.findByCorreo(correo.toLowerCase())
                .map(u -> new AuthResponse(u.getId(), u.getCorreo(), u.getNombre(),null,u.getDni(), "Login correcto (Google)", null))
                .orElseGet(() -> {
                    Usuario nuevo = Usuario.builder()
                            .correo(correo.toLowerCase())
                            .nombre(nombre != null ? nombre : correo)
                            .telefono("0000000000")
                            // guardamos una contraseña aleatoria (no usada)
                            .contrasena(passwordEncoder.encode(java.util.UUID.randomUUID().toString()))
                            .fechaRegistro(LocalDateTime.now())
                            .build();
                    usuarioRepository.save(nuevo);
                    return new AuthResponse(nuevo.getId(), nuevo.getCorreo(), nuevo.getNombre(), nuevo.getTelefono(),null, "Usuario creado por Google", null);
                });
    }
}