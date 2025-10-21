package com.example.service;

import java.util.List;

import com.example.entity.Horario;

public interface HorarioService {

	public List<Horario> listarHorariosPorIdMedico(Integer idMedico);
	
	public Horario editarHorario(Horario horario);
	
	public Horario registrarHorario(Horario horario);
	
	public void eliminarHorarioPorId(Integer idHorario);
	
}
