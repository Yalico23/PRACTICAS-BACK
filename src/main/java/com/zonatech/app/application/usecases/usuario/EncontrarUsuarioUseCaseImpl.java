package com.zonatech.app.application.usecases.usuario;

import com.zonatech.app.domain.exceptions.UsuarioNoEncontradoException;
import com.zonatech.app.domain.models.Usuario;
import com.zonatech.app.domain.ports.input.usuario.EncontrarUsuarioUseCase;
import com.zonatech.app.domain.ports.output.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EncontrarUsuarioUseCaseImpl implements EncontrarUsuarioUseCase {

    private final UsuarioRepositoryPort repositoryPort;

    @Override
    public Usuario buscarUsuarioEmail(String email) {
        return repositoryPort.findByEmail(email).orElseThrow(
                () -> new UsuarioNoEncontradoException("El usuario existe con el correo: " + email)
        );
    }
}
