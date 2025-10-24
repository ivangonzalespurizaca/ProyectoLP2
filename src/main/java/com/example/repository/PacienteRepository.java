package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Integer> {
	List<Paciente> findByNombreStartingWithIgnoreCase(String nombre);
}
