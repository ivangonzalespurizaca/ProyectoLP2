package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Horario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {
	
	List<Horario> findByMedico_idMedico(Integer idMedico);
}
