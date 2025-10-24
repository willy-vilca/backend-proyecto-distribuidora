package com.distribuidora.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CambioPasswordDTO {
    private String email;
    private String oldPassword;
    private String newPassword;
}
