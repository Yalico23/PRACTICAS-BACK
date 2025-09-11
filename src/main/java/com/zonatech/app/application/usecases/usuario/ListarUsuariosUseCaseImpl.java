package com.zonatech.app.application.usecases.usuario;

import com.zonatech.app.domain.models.Usuario;
import com.zonatech.app.domain.ports.input.usuario.ListarUsuarioUseCase;
import com.zonatech.app.domain.ports.output.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListarUsuariosUseCaseImpl implements ListarUsuarioUseCase {

    private final UsuarioRepositoryPort repositoryPort;

    @Override
    public List<Usuario> listarUsuarios() {
        return repositoryPort.findAll();
    }
}