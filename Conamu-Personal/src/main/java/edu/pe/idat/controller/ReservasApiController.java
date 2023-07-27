package edu.pe.idat.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pe.idat.model.Reserva;
import edu.pe.idat.request.ReservaUpdateRequest;
import edu.pe.idat.request.UsuarioUpdateRequest;
import edu.pe.idat.response.TransactionResponse;
import edu.pe.idat.service.ReservaService;

@RestController
@RequestMapping("/api/reservas")
public class ReservasApiController {
	
	@Autowired
	private ReservaService reservaService;
	
	@PostMapping("/update")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PERSONAL')")
	public TransactionResponse updateUsuario(@RequestBody ReservaUpdateRequest request) {
		try {
			
			reservaService.update(request);
			
			return new TransactionResponse(true, "Usuario actualizado con éxito.");
			
		}catch(Exception e) {
			
			return new TransactionResponse(false, "Ocurrió un error al actualizado el Usuario: ".concat(e.getMessage()));
			
		}
	}
	
	@GetMapping("/paged")
	//@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PERSONAL')")
	public Page<Reserva> getReservas(@RequestParam String nombres, @PageableDefault Pageable pageable) {
		return reservaService.findAllByConsumidorPersonaNombreCompletosContaining(nombres, pageable);
	}
	
}
