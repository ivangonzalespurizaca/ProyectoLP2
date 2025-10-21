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

		return horarioRepository.save(horario);
	}
}
