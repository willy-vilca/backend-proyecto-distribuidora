package com.distribuidora.backend.service;

import com.distribuidora.backend.dto.AuthResponse;
import com.distribuidora.backend.dto.LoginRequest;
import com.distribuidora.backend.dto.RegisterRequest;
import com.distribuidora.backend.dto.CambioPasswordDTO;
import org.springframework.http.ResponseEntity;

public interface UsuarioService {
    AuthResponse register(RegisterRequest req);
    AuthResponse login(LoginRequest req);
    AuthResponse loginOrRegisterGoogle(String correo, String nombre);
    ResponseEntity<?> cambiarPassword(CambioPasswordDTO cambioPasswordDTO);
}
