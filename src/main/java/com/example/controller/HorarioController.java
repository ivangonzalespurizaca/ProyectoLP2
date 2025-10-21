package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.Horario;
import com.example.entity.Medico;
import com.example.service.HorarioService;
import com.example.service.MedicoService;

@Controller
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private MedicoService medicoService;

    @GetMapping("/gestionarHorarios")
    public String gestionarHorarios(@RequestParam(required = false) Integer idMedico, Model model) {
        Medico medico = (idMedico != null) ? medicoService.buscarMedicoPorId(idMedico) : null;
        model.addAttribute("medico", medico);

        if (medico != null)
            model.addAttribute("listaHorarios", horarioService.listarHorariosPorIdMedico(idMedico));
        else
            model.addAttribute("listaHorarios", List.of());

        Horario nuevoHorario = new Horario();
        nuevoHorario.setMedico(medico);
        model.addAttribute("nuevoHorario", nuevoHorario);

        List<Medico> listaMedicos = medicoService.listarTodoMedico();
        model.addAttribute("listaMedicos", listaMedicos);

        return "Admin/horario/gestionarHorarios";
    }


	@GetMapping("/medico/buscarJson")
	@ResponseBody
	public List<Medico> buscarMedicoJson(@RequestParam(required =false) String nombre) {
	    if (nombre == null || nombre.trim().isEmpty()) {
	        return medicoService.listarTodoMedico();
	    }
		return medicoService.buscarMedicoPorNombre(nombre);
	}

	@PostMapping("/registrar")
	public String registrarHorario(@ModelAttribute Horario horario, RedirectAttributes redirect) {
	    try {
	        if (horario.getMedico() == null || horario.getMedico().getIdMedico() == null) {
	            redirect.addFlashAttribute("error", "Debe seleccionar un médico antes de registrar un horario.");
	            return "redirect:/gestionarHorarios";
	        }

	        if (horario.getIdHorario() == null) {
	            horarioService.registrarHorario(horario);
	            redirect.addFlashAttribute("success", "Horario registrado correctamente.");
	        } else {
	            horarioService.editarHorario(horario);
	            redirect.addFlashAttribute("success", "Horario actualizado correctamente.");
	        }
	    } catch (Exception e) {
	        redirect.addFlashAttribute("error", "Error al registrar o actualizar el horario.");
	    }
	    return "redirect:/gestionarHorarios?idMedico=" + horario.getMedico().getIdMedico();
	}


    @GetMapping("/eliminar/{id}")
    public String eliminarHorario(@PathVariable Integer id, @RequestParam Integer idMedico, RedirectAttributes redirect) {
        horarioService.eliminarHorarioPorId(id);
        redirect.addFlashAttribute("success", "Horario eliminado correctamente");
        return "redirect:/gestionarHorarios?idMedico=" + idMedico;
    }
}
