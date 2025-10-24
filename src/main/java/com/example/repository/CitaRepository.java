package com.example.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Cita;
import com.example.entity.Medico;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
	@Query("SELECT c FROM Cita c WHERE LOWER(c.paciente.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) " +
		       "OR LOWER(c.paciente.apellido) LIKE LOWER(CONCAT('%', :texto, '%'))")
		List<Cita> buscarPorNombreOApellido(@Param("texto") String texto);

	 List<Cita> findByMedicoAndFechaAndHora(Medico medico, LocalDate fecha, LocalTime hora);

	    @Query("SELECT c FROM Cita c WHERE c.medico.idMedico = :idMedico AND c.fecha = :fecha")
	    List<Cita> findOcupadasByMedicoAndFecha(@Param("idMedico") Integer idMedico,
	                                             @Param("fecha") LocalDate fecha);

}
