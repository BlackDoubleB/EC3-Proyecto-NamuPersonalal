$(document).ready(function() {
	habilitarBtnBuscarReservas()
	habilitarBtnEditarReserva()
	habilitarNavegacionPaginacion()
});

function habilitarBtnBuscarReservas(){
	$("#btn-buscar-reserva").click(function(e){
		procesarBusquedaReservas(e)
	})
}

function habilitarBtnEditarReservaProceso(){
	$("#btn-editar-reserva").click(function(){
		procesarEditarReserva()
	})
}

function habilitarBtnEditarReserva(){
	$(".btn-editar-reserva").click(function(){
		obtenerModalEditarReserva(this)
	})
}

function habilitarNavegacionPaginacion(){
	$("#table-reservas a.anterior").click(function(e){
		paginacionAnterior(e)
	})
	
	$("#table-reservas a.pagina").click(function(e){
		paginacionPaginaActual(e)
	})
	
	$("#table-reservas a.siguiente").click(function(e){
		paginacionSiguiente(e)
	})
}

function paginacionAnterior(e){
	e.preventDefault();
	
	var paginaActual = $(e.target).attr("data-actual-page")
	
	cargarTableUsuarios(Number(paginaActual) - 1, 2)
}

function paginacionPaginaActual(e){
	e.preventDefault();
	
	var pagina = $(e.target).attr("data-page")
	var paginaActual = $(e.target).attr("data-actual-page")
	
	if(pagina == paginaActual){
		return;
	}
	
	cargarTableUsuarios(Number(pagina), 2)
}

function paginacionSiguiente(e){
	e.preventDefault();
	
	var paginaActual = $(e.target).attr("data-actual-page")
	
	cargarTableUsuarios(Number(paginaActual) + 1, 2)
}

function procesarBusquedaReservas(e){
	e.preventDefault();
	
	cargarTableUsuarios(0, 10)
}

function procesarEditarReserva(){
	const idReserva = $("#id-reserva-editar").val()
	const idEstadoReserva = $("#estado").val()
	
	if(!idReserva){
		//alert("No se encontró la Reserva a editar.")
		toastr["warning"]("No se encontró la Reserva a editar.", "Validación" )
		return
	}
	
	if(!idEstadoReserva){
		//alert("Debe seleccionar un Estado.")
		toastr["warning"]("Debe seleccionar un Estado.", "Validación" )
		return;
	}
	
	const data = {
		idReserva,
		idEstadoReserva
	}
	
	$.ajax({
		type: 'POST',
		url: '/api/reservas/update',
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(response) {
			if (!response.success) {
				//alert(response.message)
				toastr["error"](response.message, "Error" )
				return;
			}
			
			$("#modalEditar").modal("hide")
			cargarTableUsuarios(0, 10)
		},
		error: function(jqXHR) {
			console.log(jqXHR)
			
			if(jqXHR.status == 401 || jqXHR.status == 403){
				toastr["error"]( "No esta autorizado a realizar esta acción","Error"  )
				return
			}
			
			toastr["error"]( jqXHR.status, "Error"  )
		}
	});
}

function obtenerModalEditarReserva(e){
	const idReserva = $(e).attr("data-id-reserva")
	
	$.ajax({
		type: 'GET',
		url: '/reservas/modal-editar',
		data: {
			idReserva
		},
		success: function(response) {
			if (!response) {
				return;
			}

			$(".modals").html(response)
			
			$("#modalEditar").modal("show")
			
			habilitarBtnEditarReservaProceso()
		},
		error: function(jqXHR) {
			console.log(jqXHR)
			
			if(jqXHR.status == 401 || jqXHR.status == 403){
				toastr["error"]( "No esta autorizado a realizar esta acción","Error"  )
				return
			}
			
			toastr["error"]( jqXHR.status, "Error"  )
		}
	});
}

function cargarTableUsuarios(page, size) {
	const nombres = $("#nombres-buscar-reserva").val()
	
	$.ajax({
		type: 'GET',
		url: '/reservas/table',
		data: {
			nombres,
			page,
			size
		},
		success: function(response) {
			if (!response) {
				return;
			}

			$("#table-reservas").html(response)
			
			habilitarBtnEditarReserva()
			habilitarNavegacionPaginacion()
		},
		error: function(jqXHR) {
			console.log(jqXHR)
			
			if(jqXHR.status == 401 || jqXHR.status == 403){
				toastr["error"]( "No esta autorizado a realizar esta acción","Error"  )
				return
			}
			
			toastr["error"]( jqXHR.status, "Error"  )
		}
	});
}