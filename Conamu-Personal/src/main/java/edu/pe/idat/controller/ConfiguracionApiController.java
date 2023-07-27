package edu.pe.idat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pe.idat.model.Reserva;
import edu.pe.idat.request.HorarioReservaCreateRequest;
import edu.pe.idat.response.TransactionResponse;
import edu.pe.idat.service.HorarioReservaService;
import edu.pe.idat.service.ReservaService;

@RestController
@RequestMapping("/api/horario-reserva")
public class ConfiguracionApiController {
	
	@Autowired
	private HorarioReservaService horarioReservaService;
	
	@Autowired
	private ReservaService reservaService;
	
	@PostMapping("/create")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PERSONAL')")
	public TransactionResponse createHorarioReserva(@RequestBody HorarioReservaCreateRequest request) {
		try {
			
			horarioReservaService.create(request);
			
			return new TransactionResponse(true, "Horario de Reserva creado con éxito.");
			
		}catch(Exception e) {
			
			return new TransactionResponse(false, "Ocurrió un error al registrar el Horario de Reserva.");
			
		}
	}
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PERSONAL')")
	public TransactionResponse deleteHorarioReserva(@RequestParam("idHorario") String idHorario) {
		try {
			
			List<Reserva> reservas = reservaService.findAllByHorarioReservaIdHorarioReserva(Integer.parseInt(idHorario));
			
			if(!reservas.isEmpty()) {
				return new TransactionResponse(false, "No se puede eliminar el Horario de Reserva debido a que hay Reservas registrar con este horario.");
			}
			
			horarioReservaService.delete(Integer.parseInt(idHorario));
			
			return new TransactionResponse(true, "Horario de Reserva eliminado con éxito.");
			
		}catch(Exception e) {
			
			return new TransactionResponse(false, "Ocurrió un error al eliminar el Horario de Reserva.");
			
		}
	}
}
