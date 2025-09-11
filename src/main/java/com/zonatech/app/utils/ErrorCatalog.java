package com.zonatech.app.utils;

import lombok.Getter;

@Getter
public enum ErrorCatalog {

    INVALID_CREDENCIALES("INVALID_CREDENCIALES_001", "Credenciales inválidas"),
    CORREO_NOT_FOUND("CUENTA_NOT_FOUND_002", "Correo no encontrado"),
    CORREO_YA_EXISTE("USUARIO_YA_EXISTE_003", "El usuario ya existe"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR_004", "Un error inesperado ocurrió"),
    NO_PUEDE_SER_NULL("NO_PUEDE_SER_NULL_005", "El campo no puede ser nulo"),
    NO_PUEDE_SER_VACIO("NO_PUEDE_SER_VACIO_006", "El campo no puede estar vacío");

    private final String code;
    private final String message;

    ErrorCatalog(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
