package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.Cita;
import com.example.entity.ComprobanteDePago;
import com.example.entity.enums.EstadoCita;
import com.example.entity.enums.MetodoPago;
import com.example.service.CitaService;
import com.example.service.ComprobanteDePagoService;

@Controller
@RequestMapping("/cajero/comprobanteDePago")
public class ComprobanteDePagoController {

	@Autowired
	private ComprobanteDePagoService comprobanteService;
	
	@Autowired
	private CitaService citaService;
	
	@GetMapping("/listar")
	public String listaComprobanteDePago(Model model, @RequestParam(required = false) String texto) {
	    List<com.example.entity.ComprobanteDePago> comprobantes;
	    if (texto != null && !texto.isBlank()) {
	        comprobantes = comprobanteService.buscarPorDniONombre(texto);
	    } else {
	        comprobantes = comprobanteService.listarTodos();
	    }
	    model.addAttribute("comprobantes", comprobantes);
	    model.addAttribute("texto", texto);
	    return "Cajero/comprobanteDePago/listaDeComprobantes";
	}
	
    // --- Mostrar formulario para registrar ---
    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("comprobante", new ComprobanteDePago());
        model.addAttribute("metodosPago", MetodoPago.values());
        return "Cajero/comprobanteDePago/registrarComprobante";
    }
    
    @GetMapping(value = "/buscar", produces = "application/json")
    @ResponseBody
    public List<Cita> buscarCitas(@RequestParam(required = false) String texto) {
        if (texto == null || texto.isEmpty()) {
        	EstadoCita estadoEnum = EstadoCita.valueOf("PENDIENTE");
            return citaService.listarPorEstado(estadoEnum);
        } else {
            return citaService.buscarPendientesPorPaciente("PENDIENTE", texto);
        }
    }
    
    @PostMapping("/guardar")
    public String guardarComprobante(@ModelAttribute ComprobanteDePago comprobantePago, RedirectAttributes redirectAttrs) {
        // Asociar la cita seleccionada
        if (comprobantePago.getIdCita() != null) {
            Cita cita = citaService.buscarCitaPorId(comprobantePago.getIdCita());
            comprobantePago.setCita(cita);
        }

        // Guardar el comprobante
        comprobanteService.guardarComprobante(comprobantePago);

        // Mensaje de éxito
        redirectAttrs.addFlashAttribute("success", "Comprobante registrado correctamente!");
        return "redirect:/cajero/comprobanteDePago/listar";
    }
    
    @GetMapping("/anular")
    public String anularComprobante(@RequestParam("id") Integer idComprobante,
                                    RedirectAttributes redirectAttrs) {
        try {
            // Llamamos al servicio que maneja la anulación
            comprobanteService.anularComprobante(idComprobante);

            // Mensaje de éxito
            redirectAttrs.addFlashAttribute("success", "Comprobante anulado correctamente y la cita volvió a pendiente.");
        } catch (IllegalArgumentException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Ocurrió un error al anular el comprobante.");
        }

        // Redirigimos a la lista de comprobantes
        return "redirect:/cajero/comprobanteDePago/listar";
    }
    
    @GetMapping("/detalle/{id}")
    public String verDetalleComprobante(@PathVariable("id") Integer id, Model model) {
        // Buscar comprobante por ID
        ComprobanteDePago comprobante = comprobanteService.buscarPorId(id);
        if (comprobante == null) {
            model.addAttribute("error", "Comprobante no encontrado");
            return "redirect:/cajero/comprobanteDePago/listar";
        }

        // Pasar el comprobante al modelo para la vista
        model.addAttribute("comprobante", comprobante);

        // Retornar la vista detalle
        return "Cajero/comprobanteDePago/detalleComprobante";
    }
}