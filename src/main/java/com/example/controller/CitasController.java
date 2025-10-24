package com.example.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

import com.example.entity.Cita;
import com.example.entity.Horario;
import com.example.entity.Medico;
import com.example.entity.Paciente;
import com.example.entity.Usuario;
import com.example.service.CitaService;
import com.example.service.HorarioService;
import com.example.service.MedicoService;
import com.example.service.PacienteService;
import com.example.service.UsuarioService;

@Controller
@RequestMapping("/recepcionista/cita")
public class CitasController {
	@Autowired
	private CitaService citaService;

	@Autowired 
	private PacienteService pacienteService;

	@Autowired
	private MedicoService medicoService;

	@Autowired 
	private HorarioService horarioService;

	@Autowired 
	private UsuarioService usuarioService;


	@GetMapping({"/listar"})
	public String listaCitas(Model model, @RequestParam(required = false) String texto) {
	    if (texto != null && !texto.isEmpty()) {
	        model.addAttribute("citas", citaService.buscarCitasPorPaciente(texto));
	    } else {
	        model.addAttribute("citas", citaService.listarTodoCitas());
	    }
	    model.addAttribute("texto", texto); 
	    return "Recepcionista/cita/listaDeCitas";
	}

	@GetMapping("/registrar")
	public String nuevoCita(Model model) {
	    model.addAttribute("cita", new Cita());
	    
	    // Agregar listas para los modales
	    model.addAttribute("medicos", medicoService.listarTodosMedicos());
	    model.addAttribute("pacientes", pacienteService.listarTodosPaciente());
	    
	    return "Recepcionista/cita/registrarCita"; 
	}


	@PostMapping("/guardar")
	public String guardarCita(Cita cita, Authentication auth, Model model, RedirectAttributes redirectAttrs) {
	    try {
	        // Obtener usuario logueado
	        Usuario usuarioActual = usuarioService.buscarPorUserName(auth.getName());
	        cita.setUsuario(usuarioActual);

	        // Guardar cita
	        citaService.registrarCita(cita);

	        // ✅ Mensaje de éxito -> se muestra en la página LISTAR (porque hay redirect)
	        redirectAttrs.addFlashAttribute("success", "Cita registrada correctamente!");
	        return "redirect:/recepcionista/cita/listar";

	    } catch (IllegalArgumentException | IllegalStateException e) {
	        // Mensaje de error, sin salir de la página
	        model.addAttribute("error", e.getMessage());
	        model.addAttribute("cita", cita); // mantener datos del formulario
	        return "Recepcionista/cita/registrarCita"; // tu vista actual

	    } catch (Exception e) {
	        model.addAttribute("error", "Ocurrió un error al registrar la cita.");
	        model.addAttribute("cita", cita);
	        return "Recepcionista/cita/registrarCita";  
	    }
	}

	// Listar pacientes para modal (por nombre, filtrado)
	@GetMapping(value ="/buscarPacientes", produces = "application/json")
	@ResponseBody
	public List<Paciente> buscarPacientes(@RequestParam(required = false) String texto) {
	    if (texto == null || texto.isEmpty()) {
	        return pacienteService.listarTodosPaciente();
	    } else {
	        return pacienteService.buscarPacientePorNombre(texto);
	    }
	}

	// Listar médicos para modal (por nombre, filtrado)
	@GetMapping(value = "/buscarMedicos", produces = "application/json")
	@ResponseBody
	public List<Medico> buscarMedicos(@RequestParam(required = false) String texto) {
	    if (texto == null || texto.isEmpty()) {
	        return medicoService.listarTodosMedicos();
	    } else {
	        return medicoService.buscarMedicoPorNombre(texto);
	    }
	}

	@GetMapping("/horarios")
	public String verHorarios(@RequestParam Integer idMedico,
	                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
	                          Model model) {

	    // Horarios regulares
	    List<Horario> horarios = horarioService.obtenerHorariosPorMedico(idMedico);

	    // Horarios ocupados en la fecha seleccionada
	    List<Cita> ocupadas = citaService.obtenerHorariosOcupados(idMedico, fecha);

	    model.addAttribute("horarios", horarios);
	    model.addAttribute("ocupadas", ocupadas);
	    model.addAttribute("fecha", fecha);

	    return "Recepcionista/cita/modalHorarios :: contenido";
	}

	@GetMapping("/editar/{id}")
	public String editarCita(@PathVariable Integer id, Model model) {
	    Cita cita = citaService.buscarCitaPorId(id);
	    model.addAttribute("cita", cita);
	    return "Recepcionista/cita/editarCita";
	}

	@PostMapping("/actualizar")
	public String actualizarCita(Cita cita, RedirectAttributes redirectAttributes, Authentication auth,Model model) {
	    try {
	        Cita citaExistente = citaService.buscarCitaPorId(cita.getIdCita());
	        if (citaExistente == null) {
	            redirectAttributes.addFlashAttribute("error", "La cita no existe o fue eliminada.");
	            return "redirect:/recepcionista/cita/listar";
	        }
	        Usuario usuarioActual = usuarioService.buscarPorUserName(auth.getName());
	        citaExistente.setUsuario(usuarioActual);

	        citaExistente.setMotivo(cita.getMotivo());
	        citaExistente.setFecha(cita.getFecha());
	        citaExistente.setHora(cita.getHora());

	        citaService.editarCita(citaExistente);

	        redirectAttributes.addFlashAttribute("success", "La cita fue actualizada correctamente.");
	        return "redirect:/recepcionista/cita/listar";

	    } catch (IllegalArgumentException | IllegalStateException e) {
	    	redirectAttributes.addFlashAttribute("error", e.getMessage());
	    	return "redirect:/recepcionista/cita/editar/" + cita.getIdCita();
	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("error", "Error al actualizar la cita: " + e.getMessage());
	        return "redirect:/recepcionista/cita/editar/" + cita.getIdCita(); // redirige limpio
	    }
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarCita(@PathVariable Integer id, RedirectAttributes redirect) {
		citaService.eliminar(id);
		redirect.addFlashAttribute("mensajeExito", "Cita eliminada correctamente.");
		return "redirect:/recepcionista/cita/listar";
	}



}