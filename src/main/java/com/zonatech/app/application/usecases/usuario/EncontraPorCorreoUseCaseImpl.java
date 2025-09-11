package com.zonatech.app.application.usecases.usuario;

import com.zonatech.app.domain.exceptions.CorreoNoEncontrado;
import com.zonatech.app.domain.models.Usuario;
import com.zonatech.app.domain.ports.input.usuario.EncontrarPorCorreoUseCase;
import com.zonatech.app.domain.ports.output.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EncontraPorCorreoUseCaseImpl implements EncontrarPorCorreoUseCase {

    private final UsuarioRepositoryPort repositoryPort;

    @Override
    public Usuario encontrarPorCorreo(String correo) {

        return repositoryPort.findByEmail(correo)
                .orElseThrow(() -> new CorreoNoEncontrado("Correo no encontrado: " + correo));
    }
}
