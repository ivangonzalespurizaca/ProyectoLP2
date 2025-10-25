package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.entity.Cita;
import com.example.entity.ComprobanteDePago;
import com.example.entity.enums.EstadoCita;
import com.example.entity.enums.EstadoComprobante;
import com.example.repository.ComprobanteDePagoRepository;
import com.example.service.CitaService;
import com.example.service.ComprobanteDePagoService;

import jakarta.transaction.Transactional;

@Service
public class ComprobanteDePagoServiceImpl implements ComprobanteDePagoService{

    @Autowired
    private CitaService citaService;
    
	@Autowired
	private ComprobanteDePagoRepository comprobanteDePagoRepository;
	
	@Override
	public ComprobanteDePago guardarComprobante(ComprobanteDePago comprobante) {
		return comprobanteDePagoRepository.save(comprobante);
	}

	@Override
	public List<ComprobanteDePago> listarTodos() {
		return comprobanteDePagoRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaEmision"));
	}

	@Override
	public List<ComprobanteDePago> buscarPorDniONombre(String dato) {
		return comprobanteDePagoRepository.buscarPorDniONombre(dato);
	}
	
	@Transactional
	public void anularComprobante(Integer idComprobante) {
	    ComprobanteDePago cdp = comprobanteDePagoRepository.findById(idComprobante)
	            .orElseThrow(() -> new IllegalArgumentException("Comprobante no encontrado"));

	    cdp.setEstado(EstadoComprobante.ANULADO);
	    comprobanteDePagoRepository.save(cdp);

	    Cita cita = cdp.getCita();
	    if (cita != null) {
	        citaService.actualizarEstadoCita(cita, EstadoCita.CANCELADO);
	    }
	}

	@Override
	public ComprobanteDePago buscarPorId(Integer idComprobante) {
		return comprobanteDePagoRepository.findById(idComprobante).get();
	}
}
