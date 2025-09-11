package com.zonatech.app.application.usecases.usuario;

import com.zonatech.app.domain.exceptions.CorreoYaRegistradoException;
import com.zonatech.app.domain.models.Roles;
import com.zonatech.app.domain.models.Usuario;
import com.zonatech.app.domain.ports.input.usuario.CrearUsuarioUseCase;
import com.zonatech.app.domain.ports.output.RolesRepositoryPort;
import com.zonatech.app.domain.ports.output.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
public class CrearUsuarioUseCaseImpl implements CrearUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final RolesRepositoryPort rolesRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        Optional<Usuario> existente = usuarioRepositoryPort.findByEmail(usuario.getEmail());
        if (existente.isPresent()) {
            throw new CorreoYaRegistradoException("El correo ya está registrado por otro usuario.");
        }

        List<Roles> rolesList = new ArrayList<>();
        if(usuario.isMentor()){
            Optional<Roles> mentorRole = rolesRepositoryPort.findByNombre("ROLE_MENTOR");
            mentorRole.ifPresent(rolesList::add);
        }else{
            Optional<Roles> roles = rolesRepositoryPort.findByNombre("ROLE_ESTUDIANTE");
            roles.ifPresent(rolesList::add);
        }
        usuario.setFechaCreacion(LocalDate.now());
        usuario.setToken(generarToken());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRoles(rolesList);
        usuario.setHabilitado(false);
        log.info("Creando usuario: {}", usuario);
        return usuarioRepositoryPort.save(usuario);
    }

    private String generarToken(){
        String token;
        do {
            int random = ThreadLocalRandom.current().nextInt(100000, 1000000);
            token = String.valueOf(random);
        }while (usuarioRepositoryPort.existsByToken(token));
        return token;
    }
}
