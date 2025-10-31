package com.example.service;

import com.example.entity.Usuario;
import com.example.repository.UsuarioRepository;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarUsuario(Usuario usuario) {
        String hash = passwordEncoder.encode(usuario.getContrasenia());
        usuario.setContrasenia(hash);
        return usuarioRepository.save(usuario);
    }
    
    public Usuario actualizarUsuario(Usuario usuarioActualizado, Usuario usuarioActual) {
        Usuario usuarioExistente = usuarioRepository.findById(usuarioActualizado.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioExistente.setUsername(usuarioActualizado.getUsername());
        usuarioExistente.setNombres(usuarioActualizado.getNombres());
        usuarioExistente.setApellidos(usuarioActualizado.getApellidos());
        usuarioExistente.setDni(usuarioActualizado.getDni());
        usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
        usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
        usuarioExistente.setImgPerfil(usuarioActualizado.getImgPerfil());

        // si el usuario está actualizando su propio perfil → mantener su rol
        if (usuarioActualizado.getId().equals(usuarioActual.getId())) {
            usuarioExistente.setRol(usuarioExistente.getRol());
        } else {
            usuarioExistente.setRol(usuarioActualizado.getRol());
        }

        if (usuarioActualizado.getContrasenia() != null && !usuarioActualizado.getContrasenia().isEmpty()) {
            usuarioExistente.setContrasenia(passwordEncoder.encode(usuarioActualizado.getContrasenia()));
        }

        return usuarioRepository.save(usuarioExistente);
    }

    
    public Usuario buscarPorNombre(String nombre) {
        Usuario usuario = usuarioRepository.findByUsername(nombre);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return usuario;
    }
    
    public Usuario buscarPorUserName(String username) {
    	return usuarioRepository.findByUsername(username);
    }
}
