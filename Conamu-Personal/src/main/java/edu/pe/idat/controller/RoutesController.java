package edu.pe.idat.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import edu.pe.idat.model.HorarioReserva;
import edu.pe.idat.model.Reserva;
import edu.pe.idat.model.Trabajador;
import edu.pe.idat.service.EstadoReservaService;
import edu.pe.idat.service.EstadoTrabajadorService;
import edu.pe.idat.service.HorarioReservaService;
import edu.pe.idat.service.ReservaService;
import edu.pe.idat.service.RolesService;
import edu.pe.idat.service.TrabajadorService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RoutesController {

	@Autowired
	private HorarioReservaService horarioReservaService;

	@Autowired
	private TrabajadorService trabajadorService;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private EstadoTrabajadorService estadoTrabajadorService;

	@Autowired
	private ReservaService reservaService;

	@Autowired
	private EstadoReservaService estadoReservaService;

	
//	El controlador maneja diferentes rutas, como "/configuracion", "/reservas", "/disponibilidad", "/usuarios", etc.
//  Responde con las vistas adecuadas según la ruta solicitada.
	
	
//  Retorna la vista "index" que es la página de inicio de la aplicación.	
	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}

// Maneja la solicitud para la ruta "/configuracion". Esta ruta está protegida y solo puede ser accedida por usuarios con roles "ADMINISTRADOR" o "PERSONAL". 
// Obtiene información sobre el usuario que ha iniciado sesión y los horarios de reserva desde el servicio correspondiente. Luego, retorna la vista "configuracion" junto con los datos obtenidos.
	@GetMapping("/configuracion")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PERSONAL')")
	public String configuracion(Model model, HttpServletRequest request) {
		var nombre = request.getUserPrincipal().getName();
		var usuario = trabajadorService.findOneByUsuario(nombre);

		model.addAttribute("usuario", usuario);
		model.addAttribute("horarios", horarioReservaService.findAll());

		return "configuracion";
	}

	@GetMapping("/reservas")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PERSONAL')")
	public String reservas(Model model, HttpServletRequest request,
			@PageableDefault Pageable pageable) {

		var nombre = request.getUserPrincipal().getName();
		var usuario = trabajadorService.findOneByUsuario(nombre);

		var paged = reservaService.findAllByConsumidorPersonaNombreCompletosContaining("", pageable);

		List<Integer> pages = new ArrayList<>();

		var totalPages = paged.getTotalPages();
		var paginaActual = paged.getNumber();

		if (totalPages == 1) {
			pages.add(1);
		} else if (totalPages == 2) {
			pages.add(1);
			pages.add(2);
		} else if (totalPages == 3) {
			pages.add(1);
			pages.add(2);
			pages.add(3);
		} else {
			if(paginaActual == 0) {
				pages.add(paginaActual + 1);
				pages.add(paginaActual + 2);
				pages.add(paginaActual + 3);
			}
			else if (totalPages == paginaActual + 1) {
				pages.add(totalPages - 2);
				pages.add(totalPages - 1);
				pages.add(totalPages);
			} else {
				pages.add(paginaActual);
				pages.add(paginaActual + 1);
				pages.add(paginaActual + 2);
			}
		}
		
		var legend = "Página " + (paginaActual + 1) + " de " + (totalPages) + " (" + paged.getTotalElements() + " filas)";

		model.addAttribute("paged", paged);
		model.addAttribute("usuario", usuario);
		model.addAttribute("pages", pages);
		model.addAttribute("legend", legend);

		return "reservas";
	}

	@GetMapping("/reservas/table")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PERSONAL')")
	public String obtenerTableReservas(Model model, @Param("nombres") String nombres,
			@PageableDefault Pageable pageable) throws ParseException {

		Page<Reserva> paged = reservaService.findAllByConsumidorPersonaNombreCompletosContaining(nombres, pageable);

		List<Integer> pages = new ArrayList<>();

		var totalPages = paged.getTotalPages();
		var paginaActual = paged.getNumber();

		if (totalPages == 1) {
			pages.add(1);
		} else if (totalPages == 2) {
			pages.add(1);
			pages.add(2);
		} else if (totalPages == 3) {
			pages.add(1);
			pages.add(2);
			pages.add(3);
		} else {
			if(paginaActual == 0) {
				pages.add(paginaActual + 1);
				pages.add(paginaActual + 2);
				pages.add(paginaActual + 3);
			}
			else if (totalPages == paginaActual + 1) {
				pages.add(totalPages - 2);
				pages.add(totalPages - 1);
				pages.add(totalPages);
			} else {
				pages.add(paginaActual);
				pages.add(paginaActual + 1);
				pages.add(paginaActual + 2);
			}
		}
		
		var legend = "Página " + (paginaActual + 1) + " de " + (totalPages) + " (" + paged.getTotalElements() + " filas)";
		
		model.addAttribute("paged", paged);
		model.addAttribute("pages", pages);
		model.addAttribute("legend", legend);

		return "fragments/reservas-table :: table";

	}

	@GetMapping("/reservas/modal-editar")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PERSONAL')")
	public String obtenerModalEditar(Model model, @Param("idReserva") String idReserva) throws ParseException {

		var reserva = reservaService.findById(Integer.parseInt(idReserva));

		var estados = estadoReservaService.findAll();

		model.addAttribute("reserva", reserva);
		model.addAttribute("estados", estados);

		return "fragments/reservas-modal-editar :: modal";

	}

	@GetMapping("/disponibilidad")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PERSONAL')")
	public String disponibilidad(Model model, HttpServletRequest request) throws ParseException {

		var nombre = request.getUserPrincipal().getName();
		var usuario = trabajadorService.findOneByUsuario(nombre);

		model.addAttribute("usuario", usuario);

		Date fechaActual = new Date(System.currentTimeMillis());
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		String fechaFormateada = formato.format(fechaActual);

		List<HorarioReserva> horarios = horarioReservaService.findAllByFecha(fechaFormateada);

		List<HorarioReserva> horariosDisponibles = horarios.stream().filter(horario -> horario.getNumeroMesas() > 0)
				.collect(Collectors.toList());

		model.addAttribute("horarios", horariosDisponibles);

		return "disponibilidad";
	}

	@GetMapping("/disponibilidad/table/{fecha}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PERSONAL')")
	public String disponibilidadTable(Model model, @Param("fecha") String fecha) throws ParseException {

		List<HorarioReserva> horarios = horarioReservaService.findAllByFecha(fecha);

		List<HorarioReserva> horariosDisponibles = horarios.stream().filter(horario -> horario.getNumeroMesas() > 0)
				.collect(Collectors.toList());

		model.addAttribute("horarios", horariosDisponibles);

		return "fragments/disponibilidad-table :: table";
	}

	@GetMapping("/usuarios")
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public String usuarios(Model model, HttpServletRequest request) {

		var nombre = request.getUserPrincipal().getName();
		var usuario = trabajadorService.findOneByUsuario(nombre);

		model.addAttribute("usuario", usuario);

		List<Trabajador> trabajadores;

		try {
			trabajadores = trabajadorService.findAll();
		} catch (Exception e) {
			trabajadores = new ArrayList<Trabajador>();
		}

		model.addAttribute("trabajadores", trabajadores);

		return "usuarios";
	}

	@GetMapping("/usuarios/table")
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public String usuariosTable(Model model, @Param("nombre") String nombre) throws Exception {

		model.addAttribute("trabajadores", trabajadorService.findAllByPersonaNombreCompletosContaining(nombre));

		return "fragments/usuarios-table :: table";
	}

	@GetMapping("/usuarios/modal-crear")
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public String usuariosModalCrear(Model model) throws Exception {

		model.addAttribute("roles", rolesService.findAll());

		return "fragments/usuarios-modal-crear :: modal-crear";
	}

	@GetMapping("/usuarios/modal-editar")
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public String usuariosModalEditar(Model model, @Param("id") String idTrabajador) throws Exception {

		model.addAttribute("trabajador", trabajadorService.findById(Integer.parseInt(idTrabajador)));
		model.addAttribute("roles", rolesService.findAll());
		model.addAttribute("estados", estadoTrabajadorService.findAll());

		return "fragments/usuarios-modal-editar :: modal-editar";
	}

	@GetMapping("*")
	public String handleNotFound() {
		return "redirect:/reservas";
	}
}
