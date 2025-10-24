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
	public Especialidad guardarEspecialidad(Especialidad userEntity) {
		// TODO Auto-generated method stub
		return especialidadRepository.save(userEntity) ;
	}

	@Override
	public List<Especialidad> listarTodosEspecialidad() {
		// TODO Auto-generated method stub
		return especialidadRepository.findAll();
	}

	@Override
	public Especialidad actualizarEspecialidad(Especialidad userEntity) {
		// TODO Auto-generated method stub
		return especialidadRepository.save(userEntity);
	}

	@Override
	public void eliminarEspecialidadById(Integer id) {
		// TODO Auto-generated method stub
		
		especialidadRepository.deleteById(id);
		
	}

	@Override
	public Especialidad buscarEspecialidadById(Integer id) {
		// TODO Auto-generated method stub
		return especialidadRepository.findById(id).get();
	}



	
	
}
