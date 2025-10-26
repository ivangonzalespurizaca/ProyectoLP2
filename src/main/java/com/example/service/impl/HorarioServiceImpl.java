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
	    // Validación básica
	    if (horario.getMedico() == null || horario.getHorarioEntrada() == null || horario.getHorarioSalida() == null) {
	        throw new IllegalArgumentException("El horario debe tener médico, hora de inicio y hora de fin.");
	    }

	    if (horario.getHorarioSalida().isBefore(horario.getHorarioEntrada()) || horario.getHorarioSalida() == (horario.getHorarioEntrada())) {
	        throw new IllegalArgumentException("La hora de fin debe ser después de la hora de inicio.");
	    }

	    // Validar que no exista otro horario solapado para el mismo médico
	    List<Horario> horariosExistentes = horarioRepository.findByMedico_idMedico(horario.getMedico().getIdMedico());
	    boolean solapado = horariosExistentes.stream().anyMatch(h -> 
	        (horario.getHorarioEntrada().isBefore(h.getHorarioSalida()) && horario.getHorarioSalida().isAfter(h.getHorarioEntrada()))
	    );
	    if (solapado) {
	        throw new IllegalArgumentException("El horario se solapa con otro horario existente del médico.");
	    }

	    // Guardar horario si todo está bien
	    return horarioRepository.save(horario);
	}


	@Override
	public List<Horario> obtenerHorariosPorMedico(Integer idMedico) {
		return horarioRepository.findByMedico(idMedico);
	}
}
