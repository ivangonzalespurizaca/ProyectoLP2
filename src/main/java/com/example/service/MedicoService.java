package com.example.service;

import java.util.List;

import com.example.entity.Medico;

public interface MedicoService {

	public List<Medico> listarTodoMedico();
	
	public List<Medico> buscarMedicoPorNombre(String nombre);
	
	public Medico buscarMedicoPorId(Integer id);
}
