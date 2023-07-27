package edu.pe.idat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pe.idat.model.Rol;
import edu.pe.idat.repository.RolesRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RolesService {

	@Autowired
	private RolesRepository rolesRepository;
	
	public List<Rol> findAll(){
		
		return rolesRepository.findAll();
		
	}
	
	public Rol findById(Integer idRol) {
		
		var rol = rolesRepository.findById(idRol);
		
		if(!rol.isPresent()) {
			throw new EntityNotFoundException("No se encontró el rol con id ".concat(idRol.toString()));
		}
		
		return rol.get();
		
	}
}
