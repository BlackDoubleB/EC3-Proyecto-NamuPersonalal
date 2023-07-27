package edu.pe.idat.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import edu.pe.idat.model.HorarioReserva;
import edu.pe.idat.repository.HorarioReservaRepository;
import edu.pe.idat.request.HorarioReservaCreateRequest;

@Service
public class HorarioReservaService {

	@Autowired
	private HorarioReservaRepository horarioReservaRepository;
	
//TODO LO QUE VEREMOS A CONTINUACION SON METODOS PARA GESTIONAR LAS OPERACIONES DE LOS HORARIOS DE RESERVA
	
	
//	create Crea un nuevo horario de reserva en la base de datos con los detalles proporcionados en la solicitud.
	public void create(HorarioReservaCreateRequest request) {
		
		horarioReservaRepository.create(request.getFecha(), request.getHorario(), request.getNumeroMesas());
		
	}
	
// findAll Devuelve una lista de todos los horarios de reserva ordenados por fecha en orden descendente.
	public List<HorarioReserva> findAll(){
		
		return horarioReservaRepository.findAll(Sort.by(Direction.DESC, "Fecha"));
		
	}

	
//findAllByFecha: Busca y devuelve horarios de reserva según la fecha proporcionada.
	public List<HorarioReserva> findAllByFecha(String fecha) throws ParseException{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsedDate = dateFormat.parse(fecha);
        java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
		
		return horarioReservaRepository.findAllByFecha(sqlDate);
		
	}
//delete: Elimina un horario de reserva por su ID.	
	public void delete(Number idHorario) {
		
		horarioReservaRepository.deleteById(idHorario);
		
	}
}
