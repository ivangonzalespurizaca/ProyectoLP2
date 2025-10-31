package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Horario;
import com.example.repository.HorarioRepository;
import com.example.service.HorarioService;

@Service
public class HorarioServiceImpl implements HorarioService{

	@Autowired
	HorarioRepository horarioRepository;

	@Override
	public List<Horario> listarHorariosPorIdMedico(Integer idMedico) {
		return horarioRepository.findByMedico_idMedico(idMedico);
	}

	@Override
	public Horario editarHorario(Horario horario) {
		return horarioRepository.save(horario);
	}

	@Override
	public void eliminarHorarioPorId(Integer idHorario) {
		horarioRepository.deleteById(idHorario);
		
	}
	

	@Override
	public Horario registrarHorario(Horario horario) {
	    // 🔹 Validaciones básicas
	    if (horario == null) {
	        throw new IllegalArgumentException("El objeto horario no puede ser nulo.");
	    }

	    if (horario.getMedico() == null) {
	        throw new IllegalArgumentException("Debe seleccionar un médico.");
	    }

	    if (horario.getDiaSemana() == null) {
	        throw new IllegalArgumentException("Debe seleccionar un día válido.");
	    }

	    if (horario.getHorarioEntrada() == null || horario.getHorarioSalida() == null) {
	        throw new IllegalArgumentException("Debe especificar hora de inicio y hora de fin.");
	    }

	    if (!horario.getHorarioSalida().isAfter(horario.getHorarioEntrada())) {
	        throw new IllegalArgumentException("La hora de salida debe ser posterior a la hora de entrada.");
	    }

	    // 🔹 Buscar solo horarios del mismo médico y mismo día
	    List<Horario> horariosMismoDia = horarioRepository.findByMedico_IdMedicoAndDiaSemana(
	        horario.getMedico().getIdMedico(),
	        horario.getDiaSemana()
	    );

	    // 🔹 Validar solapamiento solo dentro del mismo día
	    boolean solapado = horariosMismoDia.stream().anyMatch(h ->
	        horario.getHorarioEntrada().isBefore(h.getHorarioSalida()) &&
	        horario.getHorarioSalida().isAfter(h.getHorarioEntrada())
	    );

	    if (solapado) {
	        throw new IllegalArgumentException(
	            "El horario se solapa con otro horario existente del mismo día para este médico."
	        );
	    }

	    // 🔹 Guardar si todo está correcto
	    return horarioRepository.save(horario);
	}




	@Override
	public List<Horario> obtenerHorariosPorMedico(Integer idMedico) {
		return horarioRepository.findByMedico(idMedico);
	}
}
