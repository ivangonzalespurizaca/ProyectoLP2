package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
    public String registrarUsuario(@ModelAttribute Usuario usuario, @RequestParam("archivoImagen") MultipartFile imgPerfil, RedirectAttributes redirectAttrs, Model model) {
        try {
        	if(!imgPerfil.isEmpty()) {
        		String folder = "uploads/";
                File directorio = new File(folder);
                if (!directorio.exists()) {
                    directorio.mkdirs();
        	}
                
                String nombreArchivo = System.currentTimeMillis() + "_" + imgPerfil.getOriginalFilename();
                Path rutaArchivo = Paths.get(folder + nombreArchivo);
                
                Files.write(rutaArchivo, imgPerfil.getBytes());
                
                usuario.setImgPerfil("/uploads/" + nombreArchivo);
        	}else {
        		usuario.setImgPerfil("/images/default-user.jpg");
        	}
        	
        	
            usuarioService.registrarUsuario(usuario);
            redirectAttrs.addFlashAttribute("mensaje", "Usuario registrado con éxito.");
            return "redirect:/usuarios/admin/home"; 
        } catch (IOException e) {
        	e.printStackTrace();
        	model.addAttribute("error", "Error al guardar la imagen.");
        }
        catch (Exception e) {
        	e.printStackTrace();
            model.addAttribute("error", "Ocurrió un error al registrar el usuario");
        }
        model.addAttribute("usuario", usuario); 
        return "Admin/registro"; 
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
    public String actualizarUsuario(@ModelAttribute Usuario usuario, @RequestParam("archivoImagen") MultipartFile imgPerfil,
                                    @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                    Model model) {

        try {
            Usuario usuarioActual = usuarioService.buscarPorNombre(user.getUsername());

            if (usuarioActual == null) {
                model.addAttribute("error", "Usuario no encontrado.");
                return "Admin/editar";
            }

            // --- Si sube una nueva imagen ---
            if (!imgPerfil.isEmpty()) {
                String folder = "uploads/";
                File directorio = new File(folder);
                if (!directorio.exists()) {
                    directorio.mkdirs();
                }

                // Generar nombre único para evitar colisiones
                String nombreArchivo = System.currentTimeMillis() + "_" + imgPerfil.getOriginalFilename();
                Path rutaArchivo = Paths.get(folder + nombreArchivo);
                Files.write(rutaArchivo, imgPerfil.getBytes());

                // Eliminar la imagen anterior si existe y no es la por defecto
                if (usuarioActual.getImgPerfil() != null
                        && !usuarioActual.getImgPerfil().equals("/images/default-user.jpg")) {
                    Path rutaAntigua = Paths.get(usuarioActual.getImgPerfil().replaceFirst("^/+", ""));
                    File archivoAntiguo = rutaAntigua.toFile();
                    if (archivoAntiguo.exists()) {
                        archivoAntiguo.delete();
                    }
                }

                // Guardar la nueva ruta
                usuario.setImgPerfil("/uploads/" + nombreArchivo);
            } else {
                // Mantiene la imagen anterior
                usuario.setImgPerfil(usuarioActual.getImgPerfil());
            }          
            
            usuarioService.actualizarUsuario(usuario, usuarioActual);

            model.addAttribute("mensaje", "Usuario actualizado con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al guardar la imagen.");
        }catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error.");
        }

        return "Admin/editar"; 
    }





    


    
}
