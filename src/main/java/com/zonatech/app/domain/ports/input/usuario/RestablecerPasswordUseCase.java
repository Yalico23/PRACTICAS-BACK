package com.zonatech.app.domain.ports.input.usuario;

public interface RestablecerPasswordUseCase {
    void emailPassword(String correoDestino);
    void restablecerPassword(String token, String password);
}
