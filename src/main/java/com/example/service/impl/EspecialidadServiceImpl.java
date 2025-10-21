package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Especialidad;
import com.example.repository.EspecialidadRepository;
import com.example.service.EspecialidadService;

@Service
public class EspecialidadServiceImpl implements EspecialidadService{
	
	@Autowired
	EspecialidadRepository especialidadRepository;

	@Override
	public List<Especialidad> listarTodoEspecialidad() {
		return especialidadRepository.findAll();
	}
}
