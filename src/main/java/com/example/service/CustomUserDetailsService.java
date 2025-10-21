package com.example.service;

import com.example.entity.Usuario;
import com.example.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1️⃣ Imprime el username que llega desde el formulario
        System.out.println("Intentando login con username: " + username);

        // 2️⃣ Busca el usuario en la base de datos
        Usuario usuario = usuarioRepository.findByUsername(username);
        System.out.println("Usuario encontrado en DB: " + usuario);

        if (usuario == null) throw new UsernameNotFoundException("Usuario no encontrado");

        // 3️⃣ Revisa el rol
        String role;
        switch (usuario.getRol()) {
            case ADMINISTRADOR -> role = "ADMINISTRADOR"; // mapeo a ROLE_ADMIN
            case RECEPCIONISTA -> role = "RECEPCIONISTA"; // mapeo a ROLE_RECEPCIONISTA
            case CAJERO -> role = "CAJERO";
            default -> throw new IllegalArgumentException("Rol desconocido");
        }
        System.out.println("Rol asignado: " + role);

        // 4️⃣ Devuelve UserDetails
        UserDetails userDetails = User.builder()
                .username(usuario.getUsername())
                .password(usuario.getContrasenia())
                .roles(role) // Spring agregará ROLE_ automáticamente
                .build();

        System.out.println("UserDetails construido: " + userDetails);
        return userDetails;
    }

    

}
