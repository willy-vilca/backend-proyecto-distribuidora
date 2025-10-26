package com.distribuidora.backend.controller;

import com.distribuidora.backend.dto.AuthResponse;
import com.distribuidora.backend.dto.LoginRequest;
import com.distribuidora.backend.dto.RegisterRequest;
import com.distribuidora.backend.dto.CambioPasswordDTO;
import com.distribuidora.backend.dto.ActualizarUsuarioDTO;
import com.distribuidora.backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(
        origins = "*",
        allowedHeaders = "*",
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS
        }
)
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterRequest req) {
        AuthResponse resp = usuarioService.register(req);
        if (resp.getUserId() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", resp.getMessage()));
        }
        return ResponseEntity.status(201).body(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest req) {
        AuthResponse resp = usuarioService.login(req);
        if (resp.getUserId() == null) {
            return ResponseEntity.status(401).body(Map.of("message", resp.getMessage()));
        }
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/cambiar-password")
    public ResponseEntity<?> cambiarPassword(@RequestBody CambioPasswordDTO dto) {
        return usuarioService.cambiarPassword(dto);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarUsuario(@RequestBody ActualizarUsuarioDTO dto) {
        return usuarioService.actualizarDatosUsuario(dto);
    }
}
