package com.zonatech.app.infrastructure.repository;

import com.zonatech.app.infrastructure.entities.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaUserDetails implements UserDetailsService {

    @Autowired
    private UsuarioEntityRepository usuarioEntityRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<UsuarioEntity> userOptional = Optional.ofNullable(usuarioEntityRepository.findByEmail(email));
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User not found with email %s", email));
        }

        UsuarioEntity user = userOptional.orElseThrow();

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .collect(Collectors.toList());

        return new User(
                user.getEmail(),
                user.getPassword(),
                user.isHabilitado(),
                true,
                true,
                true,
                authorities
        );
    }
}
