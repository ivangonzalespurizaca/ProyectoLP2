package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Usuario;
import com.example.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Página de inicio
    @GetMapping("/")
    public String home() {
        return "index"; // index.html
    }

  

    // Página de login
    @GetMapping("/login")
    public String login() {
        return "login"; // login.html
    }

    // Página para administrador
    @GetMapping("/admin/home")
    public String admin() {
        return "Admin/admin"; // admin.html
    }

    // Página para médicos
    @GetMapping("/rcpsta/home")
    public String recepcionista() {
        return "Recepcionista/recepcionista"; // recpecionista.html
    }
    
    
    @GetMapping("/cajero/home")
    public String cajero () {
        return "Cajero/cajero"; // cajero.html
    }
    
    
    
    // Formulario de registro
    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "Admin/registro"; // registro.html
    }
 

    // 🔹 Procesar formulario de registro
    @PostMapping("/admin/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        usuarioService.registrarUsuario(usuario);
        model.addAttribute("mensaje", "Usuario registrado con éxito.");
        return "redirect:/usuarios/login";
    }

    // 🔹 Mostrar lista de usuarios (si lo necesitas)
//    @GetMapping("/listar")
//    public String listarUsuarios(Model model) {
//        model.addAttribute("usuarios", usuarioService.listarUsuarios());
//        return "usuarios"; // Vista usuarios.html
//    }
    


    
}
