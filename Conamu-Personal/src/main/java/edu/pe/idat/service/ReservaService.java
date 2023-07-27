package edu.pe.idat.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.pe.idat.model.EstadoReserva;
import edu.pe.idat.model.Reserva;
import edu.pe.idat.repository.ReservaRepository;
import edu.pe.idat.request.ReservaUpdateRequest;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ReservaService {
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private EstadoReservaService estadoReservaService;
	
	public List<Reserva> findAllByHorarioReservaFecha(String fecha) throws ParseException{
		
		java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
		
		if(fecha != "") {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date parsedDate = dateFormat.parse(fecha);
			sqlDate = new java.sql.Date(parsedDate.getTime());			
		}
		
		return reservaRepository.findAllByHorarioReservaFecha(sqlDate);
		
	}
	
	public List<Reserva> findAllByHorarioReservaIdHorarioReserva(Integer idHorario){
		
		return reservaRepository.findAllByHorarioReservaIdHorarioReserva(idHorario);
		
	}
	
	public List<Reserva> findAllByConsumidorPersonaNombreCompletosContaining(String nombreCompletos){
		
		return reservaRepository.findAllByConsumidorPersonaNombreCompletosContaining(nombreCompletos);
		
	}
	
	public Page<Reserva> findAllByConsumidorPersonaNombreCompletosContaining(String nombreCompletos, Pageable pageable){
		
		return reservaRepository.findAllByConsumidorPersonaNombreCompletosContaining(nombreCompletos, pageable);
		
	}
	
	public Reserva findById(Integer idReserva) {
		
		var reserva = reservaRepository.findById(idReserva);
		
		if(!reserva.isPresent()) {
			throw new EntityNotFoundException("No se encontró el estado con id ".concat(idReserva.toString()));
		}
		
		return reserva.get();
		
	}
	
	public void update(ReservaUpdateRequest request) {
		
		Reserva reserva = findById(Integer.parseInt(request.getIdReserva()));
		
		EstadoReserva estadoReserva = estadoReservaService.findById(Integer.parseInt(request.getIdEstadoReserva()));
		
		reserva.setEstadoReserva(estadoReserva);
		
		reservaRepository.save(reserva);
		
	}
	
}
