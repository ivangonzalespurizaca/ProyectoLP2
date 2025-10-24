package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.Medico;
import com.example.service.EspecialidadService;
import com.example.service.MedicoService;

@Controller
public class MedicoController {

	@Autowired
	private MedicoService medicoService;

	@Autowired
	private EspecialidadService especialidadService;
	


	@GetMapping({"/", "/admin/medico/listar"})
    public String listMedicos(Model model) {
       	model.addAttribute("Medicos", medicoService.listarTodosMedicos());
       	model.addAttribute("especialidadList", especialidadService.listarTodosEspecialidad());	        
       	return "Admin/medico/index";
    }	  
    
    @GetMapping("/admin/medico/new")
    public String createMedico(Model model){	        
    	Medico medico = new Medico();
    	model.addAttribute("medico", medico);	        
       	model.addAttribute("especialidadList", especialidadService.listarTodosEspecialidad());	        
       	return "Admin/medico/create";	     
    }	
	
    //Guardar medico
    @PostMapping("/admin/medico")
    public String saveMedico(Medico medico, RedirectAttributes redirect) {	 
    	medicoService.guardarMedico(medico);
    	redirect.addFlashAttribute("mensaje", "Medico registrado correctamente.");   
       	return "redirect:/admin/medico/listar";
    }
	
    //Editar medico
    @GetMapping("/admin/medico/edit/{id}")
    public String editMedico(@PathVariable Integer id, Model model) {
    	Medico st = medicoService.buscarMedicoPorId(id);	       
	    model.addAttribute("medico", st);	 
	    model.addAttribute("especialidadList", especialidadService.listarTodosEspecialidad());	        
       	return "Admin/medico/edit";
    }	
	
    //Actualizar Medico existente
	@PostMapping("/admin/medico/update/{id}")
	public String updateMedico(@PathVariable Integer id, Medico medico, Model model, RedirectAttributes redirect) {
		Medico existentMedico = medicoService.buscarMedicoPorId(id);
		existentMedico.setIdMedico(id);
		existentMedico.setNombres(medico.getNombres());
		existentMedico.setApellidos(medico.getApellidos());
		existentMedico.setDni(medico.getDni());
		existentMedico.setNroColegiatura(medico.getNroColegiatura());
		existentMedico.setTelefono(medico.getTelefono());
		existentMedico.setEspecialidad(medico.getEspecialidad());
		medicoService.actualizarMedico(existentMedico);
		  redirect.addFlashAttribute("mensaje", "Medico actualizado correctamente.");
	       
	    return "redirect:/admin/medico/listar";
	}	    
	
    @GetMapping("/admin/medico/delete/{id}")
	public String deleteProducto(@PathVariable Integer id, RedirectAttributes redirect) {
	    medicoService.eliminarMedicoById(id);
	    redirect.addFlashAttribute("mensaje", "Medico eliminado correctamente.");
	    return "redirect:/admin/medico/listar";
    }	

	
}
