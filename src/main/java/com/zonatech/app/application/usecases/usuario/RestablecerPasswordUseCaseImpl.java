package com.zonatech.app.application.usecases.usuario;

import com.zonatech.app.domain.exceptions.UsuarioNoEncontradoException;
import com.zonatech.app.domain.models.Usuario;
import com.zonatech.app.domain.ports.input.usuario.RestablecerPasswordUseCase;
import com.zonatech.app.domain.ports.output.EmailServiceExternalServicePort;
import com.zonatech.app.domain.ports.output.UsuarioRepositoryPort;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class RestablecerPasswordUseCaseImpl implements RestablecerPasswordUseCase {


    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final EmailServiceExternalServicePort emailServiceExternalServicePort;
    private final String clientURL;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void emailPassword(String correoDestino) {

        Optional<Usuario> usuarioOptional = usuarioRepositoryPort.findByEmail(correoDestino);
        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("El correo no está registrado");
        }

        Usuario usuario = usuarioOptional.get();
        usuario.setToken(generarToken());
        usuarioRepositoryPort.save(usuario);
        emailServiceExternalServicePort.mandarCorreo
                ("Código para restablecer tu contraseña",
                        "oscar_maznah_23_01@hotmail.com",
                        correoDestino,
                        getPlainText(usuario.getToken()),
                        getHtmlText(usuario.getToken())
                        );
    }

    @Override
    public void restablecerPassword(String token, String nuevaPassword) {

        if(token == null ||  nuevaPassword == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "token o el nuevo password son nulos");
        }

        Usuario usuarioOptional = usuarioRepositoryPort.findByToken(token).orElseThrow(()-> new UsuarioNoEncontradoException("Usuario no Encontrado con el token : " + token));

        usuarioOptional.setPassword(passwordEncoder.encode(nuevaPassword));
        usuarioOptional.setToken("");
        usuarioRepositoryPort.save(usuarioOptional);
    }

    private String generarToken() {
        String token;
        do {
            int random = ThreadLocalRandom.current().nextInt(100000, 1000000);
            token = String.valueOf(random);
        } while (usuarioRepositoryPort.existsByToken(token));

        return token;
    }

    private String getPlainText(String token) {
        String linkRestablecer = clientURL + "/restablecer-password";
        return String.format("""
            Hola,
            Hemos recibido una solicitud para restablecer tu contraseña.
            Tu código de verificación es: %s
            También puedes hacer clic en el siguiente enlace para restablecerla:
            %s
            Si no realizaste esta solicitud, puedes ignorar este mensaje.
            – Equipo de Soporte Zonatech
            """, token, linkRestablecer);
    }

    private String getHtmlText(String token) {
        String linkRestablecer = clientURL + "/restablecer-password";
        return String.format("""
            <html>
                <body style="font-family: Arial, sans-serif; font-size: 16px; color: #333">
                    <p>Hola,</p>
                    <p>Hemos recibido una solicitud para restablecer tu contraseña.</p>
                    <p>Tu código de verificación es:</p>
                    <p style="font-size: 24px; color: #1e293b;">%s</p>
                    <p>Puedes hacer clic en el siguiente enlace para restablecer tu contraseña:</p>
                    <p><a href="%s" style="color: #2563eb;">Restablecer contraseña</a></p>
                    <p>Si no realizaste esta solicitud, puedes ignorar este mensaje.</p>
                    <br><p style="font-size: 13px; color: #6b7280;">– Equipo de Soporte Zonatech</p>
                </body>
            </html>
            """, token, linkRestablecer);
    }
}
