package com.example.service;

import java.util.List;

import com.example.entity.Especialidad;


public interface EspecialidadService {
	
	public Especialidad guardarEspecialidad(Especialidad userEntity);
	
	public List<Especialidad> listarTodosEspecialidad();
	
	public Especialidad actualizarEspecialidad(Especialidad userEntity);
	
	public void eliminarEspecialidadById(Integer id);
	
	public Especialidad buscarEspecialidadById(Integer id);
}
