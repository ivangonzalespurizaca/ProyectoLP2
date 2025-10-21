package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Paciente;
import com.example.repository.PacienteRepository;
import com.example.service.PacienteService;

@Service
public class PacienteServiceImpl implements  PacienteService {

	@Autowired
	PacienteRepository pacienteRepository;
	
	@Override
	public Paciente guardarPaciente(Paciente userEntity) {
		// TODO Auto-generated method stub
		return pacienteRepository.save(userEntity);
	}

	@Override
	public List<Paciente> listarTodosPaciente() {
		// TODO Auto-generated method stub
		return pacienteRepository.findAll();
	}

	@Override
	public Paciente actualizarPaciente(Paciente userEntity) {
		// TODO Auto-generated method stub
		return pacienteRepository.save(userEntity);
	}

	@Override
	public void eliminarPacienteById(Integer id) {
		// TODO Auto-generated method stub
		pacienteRepository.deleteById(id);
		
	}

	@Override
	public Paciente buscarPacienteById(Integer id) {
		// TODO Auto-generated method stub
		return pacienteRepository.findById(id).get();
	}

}
