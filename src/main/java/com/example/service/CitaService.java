package com.example.service;

import java.time.LocalDate;
import java.util.List;

import com.example.entity.Cita;
import com.example.entity.enums.EstadoCita;

public interface CitaService {

	public List<Cita> listarTodoCitas();
	
	public List<Cita> buscarCitasPorPaciente(String nombreApe);
	
	public Cita registrarCita(Cita cita);
	
	public List<Cita> obtenerHorariosOcupados(Integer idMedico, LocalDate fecha);
	
	public Cita editarCita(Cita cita);
	
	public Cita buscarCitaPorId(Integer idCita);
	
	public void eliminar(Integer idCita);
	
	public List<Cita> buscarPendientesPorPaciente(String estado, String dato);
	
    public List<Cita> listarPorEstado(EstadoCita estado);
    
    public void actualizarEstadoCita(Cita cita, EstadoCita nuevoEstado);
}
