package com.example.service;

import java.util.List;

import com.example.entity.Medico;

public interface MedicoService {

	public List<Medico> listarTodosMedicos();
	
	public Medico guardarMedico(Medico userEntity);
	
	public Medico actualizarMedico(Medico userEntity);
	
	public void eliminarMedicoById(Integer id);
	
	public List<Medico> buscarMedicoPorNombre(String nombre);
	
	public Medico buscarMedicoPorId(Integer id);
}
