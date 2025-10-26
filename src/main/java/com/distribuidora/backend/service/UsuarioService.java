package com.distribuidora.backend.service;

import com.distribuidora.backend.dto.AuthResponse;
import com.distribuidora.backend.dto.LoginRequest;
import com.distribuidora.backend.dto.RegisterRequest;
import com.distribuidora.backend.dto.CambioPasswordDTO;
import com.distribuidora.backend.dto.ActualizarUsuarioDTO;
import org.springframework.http.ResponseEntity;

public interface UsuarioService {
    AuthResponse register(RegisterRequest req);
    AuthResponse login(LoginRequest req);
    ResponseEntity<?> cambiarPassword(CambioPasswordDTO cambioPasswordDTO);
    ResponseEntity<?> actualizarDatosUsuario(ActualizarUsuarioDTO dto);
}
