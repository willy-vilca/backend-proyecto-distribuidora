package com.distribuidora.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private Integer userId;
    private String correo;
    private String nombre;
    private String telefono;
    private String message;

    private String token;
}
