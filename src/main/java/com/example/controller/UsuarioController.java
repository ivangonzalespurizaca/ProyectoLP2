package com.example.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String registrarUsuario(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttrs, Model model) {
        try {
            usuarioService.registrarUsuario(usuario);
            redirectAttrs.addFlashAttribute("mensaje", "Usuario registrado con éxito.");
            return "redirect:/usuarios/admin/home"; 
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error");
            model.addAttribute("usuario", usuario); 
            return "Admin/registro"; 
        }
    }

    @GetMapping("/admin/editar")
    public String buscarYMostrar(@RequestParam(required = false) String nombreBusqueda, Model model) {
        if (nombreBusqueda != null && !nombreBusqueda.isEmpty()) {
            try {
                Usuario usuario = usuarioService.buscarPorNombre(nombreBusqueda);
                model.addAttribute("usuario", usuario);
            } catch (RuntimeException e) {
                model.addAttribute("error", "Usuario no encontrado");
            }
        }
        return "Admin/editar";
    }



    @PostMapping("/admin/actualizar")
    public String actualizarUsuario(@ModelAttribute Usuario usuario,
                                    @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                    Model model) {

        try {
            Usuario usuarioActual = usuarioService.buscarPorNombre(user.getUsername());
            usuarioService.actualizarUsuario(usuario, usuarioActual);

            model.addAttribute("mensaje", "Usuario actualizado con éxito.");
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error.");
        }

        return "Admin/editar"; 
    }





    


    
}
