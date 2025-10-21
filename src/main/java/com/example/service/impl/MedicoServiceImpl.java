package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Medico;
import com.example.repository.MedicoRepository;
import com.example.service.MedicoService;

@Service
public class MedicoServiceImpl implements MedicoService{

	@Autowired
	MedicoRepository medicoRepository;
	
	@Override
	public List<Medico> listarTodoMedico() {
		return medicoRepository.findAll();
	}

	@Override
    public List<Medico> buscarMedicoPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return listarTodoMedico();
        }
        return medicoRepository.findByNombresStartingWithIgnoreCase(nombre);
    }

	@Override
	public Medico buscarMedicoPorId(Integer id) {
		return medicoRepository.findById(id).get();
	}
}
