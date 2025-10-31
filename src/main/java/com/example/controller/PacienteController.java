package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.Paciente;
import com.example.service.PacienteService;

@Controller
public class PacienteController {

    @Autowired
    private PacienteService service;

    // 🧾 Listar pacientes
    @GetMapping({"/", "/recepcionista/paciente/listar"})
    public String listPacientes(Model model) {
        model.addAttribute("pacientes", service.listarTodosPaciente());
        return "Recepcionista/paciente/index";
    }

    // ➕ Nuevo paciente
    @GetMapping("/recepcionista/paciente/new")
    public String createPaciente(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "Recepcionista/paciente/create";
    }

    // 💾 Guardar paciente nuevo
    @PostMapping("/recepcionista/paciente")
    public String savePaciente(Paciente paciente, RedirectAttributes redirect) {
    	try {
            service.guardarPaciente(paciente);
            redirect.addFlashAttribute("mensaje", "Paciente registrado correctamente.");
            return "redirect:/recepcionista/paciente/listar";
		} catch (Exception e) {
            redirect.addFlashAttribute("error", "Error al registrar Paciente.");
            return "redirect:/recepcionista/paciente/listar";
		}
    	

    }

    // ✏️ Editar paciente
    @GetMapping("/recepcionista/paciente/edit/{id}")
    public String editPaciente(@PathVariable Integer id, Model model) {
        Paciente st = service.buscarPacienteById(id);
        model.addAttribute("paciente", st);
        return "Recepcionista/paciente/edit";
    }

    // 🔄 Actualizar paciente existente
    @PostMapping("/recepcionista/paciente/update/{id}")
    public String updatePaciente(@PathVariable Integer id, Paciente paciente, RedirectAttributes redirect) {
    	try {
            Paciente existente = service.buscarPacienteById(id);
            existente.setNombre(paciente.getNombre());
            existente.setApellido(paciente.getApellido());
            existente.setDni(paciente.getDni());
            existente.setTelefono(paciente.getTelefono());
            existente.setFechaNacimiento(paciente.getFechaNacimiento());

            service.actualizarPaciente(existente);
            redirect.addFlashAttribute("mensaje", "Paciente actualizado correctamente.");
            return "redirect:/recepcionista/paciente/listar";
		} catch (Exception e) {
            redirect.addFlashAttribute("error", "Error al actualizar paciente.");
            return "redirect:/recepcionista/paciente/listar";
		}
    }

    // 🗑️ Eliminar paciente
    @GetMapping("/recepcionista/paciente/delete/{id}")
    public String deletePaciente(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            service.eliminarPacienteById(id);
            redirect.addFlashAttribute("mensaje", "Paciente eliminado correctamente.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Ocurrió un error al eliminar el paciente.");
            e.printStackTrace(); // útil para depuración
        }
        return "redirect:/recepcionista/paciente/listar";
    }

}
