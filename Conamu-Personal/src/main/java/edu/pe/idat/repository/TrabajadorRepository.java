package edu.pe.idat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pe.idat.model.Trabajador;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer> {
	
	List<Trabajador> findAllByPersonaNombreCompletosContaining(String nombres);
	
	Optional<Trabajador> findOneByUsuario(String usuario);
}
