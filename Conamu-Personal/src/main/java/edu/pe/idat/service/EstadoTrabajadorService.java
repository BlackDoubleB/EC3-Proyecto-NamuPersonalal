package edu.pe.idat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pe.idat.model.EstadoTrabajador;
import edu.pe.idat.repository.EstadoTrabajadorRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class EstadoTrabajadorService {

	@Autowired
	private EstadoTrabajadorRepository estadoTrabajadorRepository;
	
	public List<EstadoTrabajador> findAll(){
		
		return estadoTrabajadorRepository.findAll();
		
	}
	
	public EstadoTrabajador findById(Integer idEstadoTrabajador) {
		
		var estado = estadoTrabajadorRepository.findById(idEstadoTrabajador);
		
		if(!estado.isPresent()) {
			throw new EntityNotFoundException("No se encontró el estado con id ".concat(idEstadoTrabajador.toString()));
		}
		
		return estado.get();
		
	}
	
}
