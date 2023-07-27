package edu.pe.idat.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.pe.idat.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Integer>  {

	Reserva findFirstByOrderByIdReservaDesc();
	
	List<Reserva> findAllByHorarioReservaFecha(Date fecha);
	
	List<Reserva> findAllByHorarioReservaIdHorarioReserva(Integer idHorario);
	
	List<Reserva> findAllByConsumidorPersonaNombreCompletosContaining(String nombreCompletos);
	
	Page<Reserva> findAllByConsumidorPersonaNombreCompletosContaining(String nombreCompletos, Pageable pageable);
	
}
