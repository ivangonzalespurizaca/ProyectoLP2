package com.example.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Cita;
import com.example.entity.Horario;
import com.example.entity.enums.DiaSemana;
import com.example.entity.enums.EstadoCita;
import com.example.repository.CitaRepository;
import com.example.repository.HorarioRepository;
import com.example.service.CitaService;

import jakarta.transaction.Transactional;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    @Override
    public List<Cita> listarTodoCitas() {
        return citaRepository.findAll();
    }

    @Override
    public List<Cita> buscarCitasPorPaciente(String nombreApe) {
        return citaRepository.buscarPorNombreOApellido(nombreApe);
    }

    @Override
    public Cita registrarCita(Cita cita) {
        validarCita(cita, false); // false = no es edición
        cita.setEstado(EstadoCita.PENDIENTE);
        return citaRepository.save(cita);
    }

    @Override
    public Cita editarCita(Cita cita) {
        validarCita(cita, true); // true = es edición
        return citaRepository.save(cita);
    }

    /**
     * Valida las reglas de negocio de una cita, tanto para registro como edición.
     */
    private void validarCita(Cita cita, boolean esEdicion) {
        // 1. Verificar paciente y médico
        if (cita.getPaciente() == null || cita.getMedico() == null) {
            throw new IllegalArgumentException("Paciente y médico son obligatorios.");
        }

        // 2. Validar fecha/hora futura
        LocalDateTime fechaHoraCita = LocalDateTime.of(cita.getFecha(), cita.getHora());
        if (fechaHoraCita.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha y hora de la cita no pueden ser en el pasado.");
        }

        // 3. Validar disponibilidad del médico
        DiaSemana diaSemana = DiaSemana.fromLocalDate(cita.getFecha());
        List<Horario> horarios = horarioRepository.findByMedicoAndDiaSemana(cita.getMedico(), diaSemana);
        boolean disponible = horarios.stream().anyMatch(h ->
            !fechaHoraCita.toLocalTime().isBefore(h.getHorarioEntrada()) &&
            !fechaHoraCita.toLocalTime().isAfter(h.getHorarioSalida())
        );

        if (!disponible) {
            throw new IllegalStateException("El médico no atiende en ese horario.");
        }

        // 4. Validar que no haya otra cita en el mismo horario
        List<Cita> citasExistentes = citaRepository.findByMedicoAndFechaAndHora(cita.getMedico(), cita.getFecha(), cita.getHora());

        boolean hayConflicto = citasExistentes.stream().anyMatch(c -> 
            !esEdicion || !c.getIdCita().equals(cita.getIdCita()) // Ignora la misma cita si es edición
        );

        if (hayConflicto) {
            throw new IllegalStateException("El médico ya tiene otra cita en ese horario.");
        }
    }



	@Override
	public List<Cita> obtenerHorariosOcupados(Integer idMedico, LocalDate fecha) {
		return citaRepository.findOcupadasByMedicoAndFecha(idMedico, fecha);
	}

	@Override
	public Cita buscarCitaPorId(Integer idCita) {
		return citaRepository.findById(idCita).get();
	}

	@Override
	public void eliminar(Integer idCita) {
		citaRepository.deleteById(idCita);
		
	}

	@Override
	public List<Cita> buscarPendientesPorPaciente(String dato) {
		return citaRepository.buscarCitasPendientesPorPaciente(EstadoCita.PENDIENTE, dato);
	}

	@Override
	public List<Cita> listarPorEstado(EstadoCita estado) {
		return citaRepository.buscarPorEstado(estado);
	}
	
	@Override
	@Transactional
	public void actualizarEstadoCita(Cita cita, EstadoCita nuevoEstado) {
	    cita.setEstado(nuevoEstado);
	    citaRepository.save(cita); 
	}
}