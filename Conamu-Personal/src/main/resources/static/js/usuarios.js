$(document).ready(function() {
	habilitarBtnBuscar()
	habilitarBtnCrear()
	habilitarBtnEditar()
});

function habilitarBtnEditar(){
	$(".btn-editar-usuario").click(function() {
		cargarModalEditar(this)
	})
}

function habilitarBtnGuardarUsuario() {
	$("#btn-guardar-usuario").click(function() {
		procesarGuardarUsuario()
	})
}

function habilitarBtnEditarUsuario() {
	$("#btn-editar-usuario").click(function() {
		procesarEditarUsuario()
	})
}

function habilitarBtnCrear() {
	$("#btn-crear").click(function() {
		cargarModalCrear()
	})
}

function habilitarBtnBuscar() {
	$("#btn-buscar").click(function(e) {
		buscarPorNombre(e);
	});
}

function cargarModalEditar(e){
	let idTrabajador = $(e).attr("data-id-trabajador")
	
	$.ajax({
		type: 'GET',
		url: '/usuarios/modal-editar',
		data: {
			idTrabajador
		},
		success: function(response) {
			if (!response) {
				return;
			}

			$(".modals").html(response)

			$("#modalEditar").modal("show")
			
			habilitarBtnEditarUsuario()
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

function procesarEditarUsuario() {
	
	let idTrabajador = $("#id-trabajador").val()
	let telefono = $("#telefono").val()
	let correo = $("#email").val()
	let estado = $("#estado").val()

	if (!idTrabajador) {
		//alert("No se ha seleccionado un Usuario a editar.")
		toastr["warning"]("No se ha seleccionado un Usuario a editar.", "Validación" )
		return;
	}

	if (!telefono) {
		//alert("Debe ingresar el teléfono del Usuario.")
		toastr["warning"]("Debe ingresar el teléfono del Usuario.", "Validación" )
		return;
	}

	if (!correo) {
		//alert("Debe ingresar el correo del Usuario.")
		toastr["warning"]("Debe ingresar el correo del Usuario.", "Validación" )
		return;
	}

	if (!estado) {
		//alert("Debe seleccionar un Estado para el Usuario.")
		toastr["warning"]("Debe seleccionar un Estado para el Usuario.", "Validación" )
		return;
	}

	let data = {
		idTrabajador,
		telefono,
		correo,
		estado,
	}

	$.ajax({
		type: 'POST',
		url: '/api/usuario/update',
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(response) {
			if (!response.success) {
				//alert(response.message)
				toastr["error"](response.message, "Error" )
				return;
			}

			$("#modalEditar").modal("hide")
			cargarTableUsuarios("")
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

function procesarGuardarUsuario() {
	let contrasena = $("#contrasena").val()
	let confirmContrasena = $("#confirm-contrasena").val()
	let nombres = $("#name").val()
	let dni = $("#dni").val()
	let telefono = $("#telefono").val()
	let correo = $("#email").val()
	let rol = $("#rol").val()
	let usuario = $("#usuario").val()

	if (!nombres) {
		//alert("Debe ingresar los Nombres del Usuario.")
		toastr["warning"]("Debe ingresar los Nombres del Usuario.", "Validación" )
		return;
	}

	if (!dni) {
		//alert("Debe ingresar el Dni del Usuario.")
		toastr["warning"]("Debe ingresar el Dni del Usuario.", "Validación" )
		return;
	}

	if (!telefono) {
		//alert("Debe ingresar el teléfono del Usuario.")
		toastr["warning"]("Debe ingresar el teléfono del Usuario.", "Validación" )
		return;
	}

	if (!correo) {
		//alert("Debe ingresar el correo del Usuario.")
		toastr["warning"]("Debe ingresar el correo del Usuario.", "Validación" )
		return;
	}

	if (!rol) {
		//alert("Debe seleccionar un Rol para el Usuario.")
		toastr["warning"]("Debe seleccionar un Rol para el Usuario.", "Validación" )
		return;
	}

	if (!usuario) {
		//alert("Debe ingresar un Nombre de Usuario.")
		toastr["warning"]("Debe ingresar un Nombre de Usuario.", "Validación" )
		return;
	}
	
	if (!contrasena || !confirmContrasena) {
		//alert("Debe ingresar una contraseña.")
		toastr["warning"]("Debe ingresar una contraseña.", "Validación" )
		return;
	}

	if (contrasena != confirmContrasena) {
		//alert("Las contraseñas no coinciden.")
		toastr["warning"]("Las contraseñas no coinciden.", "Validación" )
		return;
	}

	let data = {
		nombreCompletos: nombres,
		dni,
		telefono,
		correo,
		rol,
		usuario,
		contrasena
	}

	$.ajax({
		type: 'POST',
		url: '/api/usuario/create',
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(response) {
			if (!response.success) {
				//alert(response.message)
				toastr["error"](response.message, "Error" )
				return;
			}

			$("#modalCrear").modal("hide")
			cargarTableUsuarios("")
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

function cargarModalCrear() {
	$.ajax({
		type: 'GET',
		url: '/usuarios/modal-crear',
		data: {},
		success: function(response) {
			if (!response) {
				return;
			}

			$(".modals").html(response)

			$("#modalCrear").modal("show")
			
			habilitarBtnGuardarUsuario()
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

function buscarPorNombre(e) {
	e.preventDefault();

	cargarTableUsuarios()
}

function cargarTableUsuarios() {
	let nombre = $("#nombres-buscar").val()
	
	$.ajax({
		type: 'GET',
		url: '/usuarios/table',
		data: {
			nombre
		},
		success: function(response) {
			if (!response) {
				return;
			}

			$(".datagrid").html(response)
			
			habilitarBtnEditar()
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