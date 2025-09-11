package com.zonatech.app.infrastructure.dto.response;

public record UsuarioByEmailDTOResponse(
        Long id,
        String nombre,
        String apellidos,
        String email
) {
}
