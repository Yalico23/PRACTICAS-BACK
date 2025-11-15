package com.zonatech.app.infrastructure.controllers;

import com.zonatech.app.application.services.UsuarioServices;
import com.zonatech.app.domain.models.Usuario;
import com.zonatech.app.infrastructure.dto.request.usuario.UsuarioRegisterRequestDTO;
import com.zonatech.app.infrastructure.dto.response.UsuarioByEmailDTOResponse;
import com.zonatech.app.infrastructure.mappers.UsuarioMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuarios")
public class UsuariosControllers {

    private final UsuarioServices usuarioServices;
    private final UsuarioMapper usuarioMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody UsuarioRegisterRequestDTO usuario) {
        Usuario nuevoUsuario = usuarioMapper.toModel(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioServices.crearUsuario(nuevoUsuario));
    }

    @PreAuthorize("hasAnyRole('MENTOR','ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioServices.listarUsuarios());
    }

    @PostMapping("/restablecer-password-email")
    public ResponseEntity<Map<String, Object>> restablecerPasswordEmail(@RequestParam("correoDestino") String correoDestino) {
        usuarioServices.emailPassword(correoDestino);
        Map<String, Object> response = Map.of(
                "mensaje", "Correo enviado exitosamente",
                "correoDestino", correoDestino
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/restablecer-password")
    public ResponseEntity<Map<String, Object>> validarToken
            (@RequestParam("token")String token,
             @RequestParam("nuevoPassword")String nuevoPassword){

        usuarioServices.restablecerPassword(token, nuevoPassword);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje","Password modificado correctamente");
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('MENTOR','ESTUDIANTE','ADMIN')")
    @GetMapping("/usuarioByEmail")
    public ResponseEntity<UsuarioByEmailDTOResponse> usuarioByEmail(@RequestParam("email")String email){
        Usuario usuario = usuarioServices.encontrarPorCorreo(email);
        UsuarioByEmailDTOResponse responseUsuario = usuarioMapper.toDTOUsuarioByEmailResponse(usuario);
        return ResponseEntity.ok(responseUsuario);
    }

}
