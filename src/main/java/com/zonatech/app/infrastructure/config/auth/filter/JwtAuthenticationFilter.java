package com.zonatech.app.infrastructure.config.auth.filter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zonatech.app.infrastructure.entities.UsuarioEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static com.zonatech.app.infrastructure.config.auth.TokenJwtConfig.*;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    // método que se ejecuta cuando se intenta autenticar
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsuarioEntity user = null;
        String email = null;
        String password = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), UsuarioEntity.class);
            email = user.getEmail();
            password = user.getPassword();
        }catch (StreamReadException | DatabindException e){
            logger.error("Error al leer el usuario", e);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        assert user != null;
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                email,
                password
        );
        return authenticationManager.authenticate(authenticationToken);
    }

    // Si la autenticación es exitosa, se ejecuta este método
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // obtenemos el usuario autenticado
        User user = (User) authResult.getPrincipal();

        String correo = user.getUsername();
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities(); // roles del usuario

        Claims claims = Jwts.claims()
                .add("authorities", new ObjectMapper().writeValueAsString(roles))
                .add("email", correo)
                .build();

        String token = Jwts.builder()
                .subject(correo)
                .claims(claims)
                .signWith(SECRET_KEY)
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(Duration.ofHours(5)))) // 5 horas
                .compact();

        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);
        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("correo", correo);
        body.put("message", String.format("Hola usuario con correo: %s, has iniciado tu sesion exitosamente", correo));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));// convertimos el body a JSON
        response.setContentType(CONTENT_TYPE);// indicamos que el contenido es de tipo JSON
        response.setStatus(200); // indicamos que la respuesta es correcta
    }

    //En caso de error en la autenticación
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Error de autenticacion: correo o password incorrecto!");
        body.put("error", failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));// convertimos el body a JSON
        response.setStatus(401); // indicamos que la respuesta es incorrecta
        response.setContentType(CONTENT_TYPE);// indicamos que el contenido es de tipo JSON
    }
}
