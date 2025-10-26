package com.distribuidora.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualizarUsuarioDTO {
    private Integer idUsuario;
    private String nombre;
    private String correo;
    private String telefono;
    private String dni;
}
