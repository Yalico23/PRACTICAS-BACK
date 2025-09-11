package com.zonatech.app.infrastructure.dto.request.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRegisterRequestDTO(
        @NotBlank(message = "El nombre no puede estar en blanco")
        String nombre,

        @NotBlank(message = "Los apellidos no pueden estar en blanco")
        String apellidos,

        @NotBlank(message = "El correo no puede estar en blanco")
        @Email(message = "El correo debe tener un formato válido")
        String email,

        @NotBlank(message = "La contraseña no puede estar en blanco")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password,

        boolean mentor
) {
}
