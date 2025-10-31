package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.entity.Especialidad;
import com.example.service.EspecialidadService;
import org.springframework.ui.Model;


@Controller
public class EspecialidadController {

    @Autowired
    private EspecialidadService service;

    // 🧾 Listar especialidad
    @GetMapping({"/", "/admin/especialidad/listar"})
    public String listEspecialidades(Model model) {
        model.addAttribute("especialidades", service.listarTodosEspecialidad());
        return "Admin/especialidad/index";
    }

    // ➕ Nuevo Especialidad
    @GetMapping("/admin/especialidad/new")
    public String createEspecialidad(Model model) {
        model.addAttribute("especialidad", new Especialidad());
        return "Admin/especialidad/create";
    }

    // 💾 Guardar Especialidad nuevo
    @PostMapping("/admin/especialidad")
    public String saveEspecialidad(Especialidad especialidad, RedirectAttributes redirect) {
    	try {
    		service.guardarEspecialidad(especialidad);
	        redirect.addFlashAttribute("mensaje", "Especialidad registrado correctamente.");
	        return "redirect:/admin/especialidad/listar";
		} catch (Exception e) {
	        redirect.addFlashAttribute("error", "Error al registrar especialidad");
	        return "redirect:/admin/especialidad/listar";
		}
    }

    // ✏️ Editar especialidad
    @GetMapping("/admin/especialidad/edit/{id}")
    public String editEspecialidad(@PathVariable Integer id, Model model) {
        Especialidad st = service.buscarEspecialidadById(id);
        model.addAttribute("especialidad", st);
        return "Admin/especialidad/edit";
    }

    // 🔄 Actualizar especialidad existente
    @PostMapping("/admin/especialidad/update/{id}")
    public String updateEspecialidad(@PathVariable Integer id, Especialidad especialidad, RedirectAttributes redirect) {
    	try {
            Especialidad existente = service.buscarEspecialidadById(id);
            existente.setNombreEspecialidad(especialidad.getNombreEspecialidad());
        
            service.actualizarEspecialidad(existente);
            redirect.addFlashAttribute("mensaje", "Especialidad actualizado correctamente.");
            return "redirect:/admin/especialidad/listar";
		} catch (Exception e) {
            redirect.addFlashAttribute("error", "Error al actualizar especialidad");
            return "redirect:/admin/especialidad/listar";
		}
    }

 // 🗑️ Eliminar especialidad
    @GetMapping("/admin/especialidad/delete/{id}")
    public String deleteEspecialidad(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            service.eliminarEspecialidadById(id); 

            redirect.addFlashAttribute("mensaje", "Especialidad eliminada correctamente.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Ocurrió un error al eliminar la especialidad.");
            e.printStackTrace(); 
        }
        return "redirect:/admin/especialidad/listar";
    }

	
	
	
}
