package com.zonatech.app.application.services;

import com.zonatech.app.domain.models.Usuario;
import com.zonatech.app.domain.ports.input.usuario.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServices implements
        ListarUsuarioUseCase,
        EncontrarPorCorreoUseCase,
        CrearUsuarioUseCase,
        RestablecerPasswordUseCase,
        EncontrarUsuarioUseCase{

    private final ListarUsuarioUseCase listarUsuarioUseCase;
    private final EncontrarPorCorreoUseCase encontrarPorCorreoUseCase;
    private final CrearUsuarioUseCase crearUsuarioUseCase;
    private final RestablecerPasswordUseCase restablecerPasswordUseCase;
    private final EncontrarUsuarioUseCase encontrarUsuario;

    public UsuarioServices(ListarUsuarioUseCase listarUsuarioUseCase,
                           EncontrarPorCorreoUseCase encontrarPorCorreoUseCase,
                           CrearUsuarioUseCase crearUsuarioUseCase,
                           RestablecerPasswordUseCase restablecerPasswordUseCase,
                           EncontrarUsuarioUseCase encontrarUsuario) {

        this.listarUsuarioUseCase = listarUsuarioUseCase;
        this.encontrarPorCorreoUseCase = encontrarPorCorreoUseCase;
        this.crearUsuarioUseCase = crearUsuarioUseCase;
        this.restablecerPasswordUseCase = restablecerPasswordUseCase;
        this.encontrarUsuario = encontrarUsuario;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Usuario> listarUsuarios() {
        return listarUsuarioUseCase.listarUsuarios();
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario encontrarPorCorreo(String correo) {
        return encontrarPorCorreoUseCase.encontrarPorCorreo(correo);
    }

    @Transactional
    @Override
    public Usuario crearUsuario(Usuario usuario) {
        return crearUsuarioUseCase.crearUsuario(usuario);
    }

    @Transactional
    @Override
    public void emailPassword(String correoDestino) {
        restablecerPasswordUseCase.emailPassword(correoDestino);
    }

    @Transactional
    @Override
    public void restablecerPassword(String token, String nuevaPassword) {
        restablecerPasswordUseCase.restablecerPassword(token, nuevaPassword);
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario buscarUsuarioEmail(String email) {
        return encontrarUsuario.buscarUsuarioEmail(email);
    }

}
