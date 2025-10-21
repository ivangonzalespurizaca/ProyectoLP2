package com.example.service;

import java.util.List;

import com.example.entity.Paciente;

public interface PacienteService {
	
	//Guarda (inserta) un nuevo paciente en la base de datos.
	public Paciente guardarPaciente(Paciente userEntity);

	//Obtiene una lista de todos los paciente registrados en la base de datos.
	public List<Paciente> listarTodosPaciente();

	//Actualiza un paciente existente con nuevos datos.
	public Paciente actualizarPaciente(Paciente userEntity);

	//Elimina un paciente de la base de datos usando su ID.
	public void eliminarPacienteById(Integer id);
	
	//Busca un paciente específico por su ID.
	public Paciente buscarPacienteById(Integer id);

}
