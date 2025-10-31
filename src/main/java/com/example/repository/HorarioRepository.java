package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Horario;
import com.example.entity.Medico;
import com.example.entity.enums.DiaSemana;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {
	List<Horario> findByMedico_idMedico(Integer idMedico);
    List<Horario> findByMedicoAndDiaSemana(Medico medico, DiaSemana diaSemana);
    
    @Query("SELECT h FROM Horario h WHERE h.medico.idMedico = :idMedico")
    List<Horario> findByMedico(@Param("idMedico") Integer idMedico);
    List<Horario> findByMedico_IdMedicoAndDiaSemana(Integer idMedico, DiaSemana dia);
}
