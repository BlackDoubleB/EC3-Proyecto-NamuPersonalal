package edu.pe.idat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pe.idat.request.UsuarioCreateRequest;
import edu.pe.idat.request.UsuarioUpdateRequest;
import edu.pe.idat.response.TransactionResponse;
import edu.pe.idat.service.TrabajadorService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioApiController {
	
	@Autowired
	private TrabajadorService trabajadorService;
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public TransactionResponse createUsuario(@RequestBody UsuarioCreateRequest request) {
		try {
			
			trabajadorService.addPersonaTrabajador(request);
			
			return new TransactionResponse(true, "Usuario creado con éxito.");
			
		}catch(Exception e) {
			
			return new TransactionResponse(false, "Ocurrió un error al registrar el Usuario: ".concat(e.getMessage()));
			
		}
	}
	
	@PostMapping("/update")
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public TransactionResponse updateUsuario(@RequestBody UsuarioUpdateRequest request) {
		try {
			
			trabajadorService.updateTrabajador(request);
			
			return new TransactionResponse(true, "Usuario actualizado con éxito.");
			
		}catch(Exception e) {
			
			return new TransactionResponse(false, "Ocurrió un error al actualizado el Usuario: ".concat(e.getMessage()));
			
		}
	}
//	logout(): Maneja la solicitud POST para la ruta "/api/usuario/logout". Este método se utiliza para cerrar la sesión de un usuario. 
//  Borra la cookie "Authorization" del navegador del cliente para desautenticar al usuario y cerrar la sesión.
	@PostMapping("/logout")
	public void logout(HttpServletResponse response) {
		Cookie cookie = new Cookie("Authorization", null);
		cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
	}
	
}
