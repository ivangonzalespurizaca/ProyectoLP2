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
	public List<Medico> listarTodosMedicos() {
		// TODO Auto-generated method stub
		return medicoRepository.findAll();
	}

	@Override
	public Medico guardarMedico(Medico userEntity) {
		// TODO Auto-generated method stub
		return medicoRepository.save(userEntity);
	}

	@Override
	public Medico actualizarMedico(Medico userEntity) {
		// TODO Auto-generated method stub
		return medicoRepository.save(userEntity);
	}

	@Override
	public void eliminarMedicoById(Integer id) {
		// TODO Auto-generated method stub
		medicoRepository.deleteById(id);
	}

	@Override
	public List<Medico> buscarMedicoPorNombre(String nombre) {
		// TODO Auto-generated method stub
		return medicoRepository.findByNombresStartingWithIgnoreCase(nombre);
	}

	@Override
	public Medico buscarMedicoPorId(Integer id) {
		// TODO Auto-generated method stub
		return medicoRepository.findById(id).get();
	}




	

}
