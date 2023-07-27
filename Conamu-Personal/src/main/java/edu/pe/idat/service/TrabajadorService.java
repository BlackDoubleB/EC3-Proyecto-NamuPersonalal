package edu.pe.idat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.pe.idat.model.EstadoTrabajador;
import edu.pe.idat.model.Persona;
import edu.pe.idat.model.Rol;
import edu.pe.idat.model.Trabajador;
import edu.pe.idat.repository.TrabajadorRepository;
import edu.pe.idat.request.UsuarioCreateRequest;
import edu.pe.idat.request.UsuarioUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class TrabajadorService {

	@Autowired
	private TrabajadorRepository trabajadorRepository;
	
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private EstadoTrabajadorService estadoTrabajadorService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	//findAll: Devuelve una lista de todos los trabajadores presentes en la base de datos.
	public List<Trabajador> findAll(){
		
		return trabajadorRepository.findAll();
		
	}
	
	
	//findOneByUsuario: Busca y devuelve un trabajador por su nombre de usuario (usuario).
	public Trabajador findOneByUsuario(String usuario) {
		var trabajador = trabajadorRepository.findOneByUsuario(usuario);
		
		if(!trabajador.isPresent()) {
			throw new EntityNotFoundException("No se encontró el Usuario con username ".concat(usuario));
		}
		
		return trabajador.get();
	}
	
	//findOneByUsuarioOptional: Busca y devuelve un trabajador por su nombre de usuario (usuario) como un objeto Optional.
	public Optional<Trabajador> findOneByUsuarioOptional(String usuario) {
		return trabajadorRepository.findOneByUsuario(usuario);
	}
	
	
	//findById: Busca y devuelve un trabajador por su ID (idTrabajador).
	public Trabajador findById(Integer idTrabajador) {
		
		var trabajador = trabajadorRepository.findById(idTrabajador);
		
		if(!trabajador.isPresent()) {
			throw new EntityNotFoundException("No se encontró el Usuario con id ".concat(idTrabajador.toString()));
		}
		
		return trabajador.get();
		
	}
	
	
	//findAllByPersonaNombreCompletosContaining: Devuelve una lista de trabajadores cuyos nombres contienen la cadena proporcionada (nombres).
	public List<Trabajador> findAllByPersonaNombreCompletosContaining(String nombres){
		
		return trabajadorRepository.findAllByPersonaNombreCompletosContaining(nombres);
		
	}
	
	//addPersonaTrabajador: Agrega un nuevo trabajador a la base de datos con los detalles proporcionados en la solicitud (request).
	//También crea una nueva persona asociada al trabajador y guarda ambas entidades en la base de datos.
	@Transactional
	public Trabajador addPersonaTrabajador (UsuarioCreateRequest request) {
		
		Persona persona = new Persona();
		persona.setDni(request.getDni());
		persona.setCorreo(request.getCorreo());
		persona.setNombreCompletos(request.getNombreCompletos());
		persona.setTelefono(request.getTelefono());
		
		Rol rol = rolesService.findById(Integer.parseInt(request.getRol()));
		
		EstadoTrabajador estado = estadoTrabajadorService.findById(1);
		
		Trabajador trabajador = new Trabajador();
		trabajador.setUsuario(request.getUsuario());
		trabajador.setContrasena(passwordEncoder.encode(request.getContrasena()));
		trabajador.setPersona(persona);
		trabajador.setRol(rol);
		trabajador.setEstadoTrabajador(estado);
		
		personaService.addPersona(persona);
		
		return trabajadorRepository.save(trabajador);
		
	}
	
	//updateTrabajador: Actualiza un trabajador existente en la base de datos con la información proporcionada en la solicitud (request). 
	//Actualiza los detalles del trabajador, como el correo, el estado, etc., y guarda los cambios en la base de datos.
	@Transactional
	public void updateTrabajador(UsuarioUpdateRequest request) {
		
		Trabajador trabajador = findById(Integer.parseInt(request.getIdTrabajador()));
		
		trabajador.getPersona().setTelefono(request.getTelefono());
		trabajador.getPersona().setCorreo(request.getCorreo());
		
		EstadoTrabajador estado = estadoTrabajadorService.findById(Integer.parseInt(request.getEstado()));
		
		trabajador.setEstadoTrabajador(estado);
		
		trabajadorRepository.save(trabajador);
		
	}
}
