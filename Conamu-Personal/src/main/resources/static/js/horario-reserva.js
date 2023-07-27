$(document).ready(function() { 
    habilitarBtnRegistrarHorarioReserva();
    habilitarBtnEliminarHorarioReserva();
    habilitarBtnConfirmarEliminarHorario();
    
    setDefaultValues();
});

//configura los campos para agregar horarios a sus valores por defecto
function setDefaultValues(){
	let fechaInput = document.getElementById("fecha-reserva")
	let horarioInput = document.getElementById("horario-reserva")
	let mesasInput = document.getElementById("mesas-reserva")
	
	fechaInput.value = dayjs().format("YYYY-MM-DD")
	fechaInput.min = dayjs().format("YYYY-MM-DD")
	
	horarioInput.value = dayjs().format("HH:mm")
	
	mesasInput.value = 1
}

function habilitarBtnConfirmarEliminarHorario(){
	$("#confirmar-eliminar-horario").click(function(){
		confirmarEliminarHorario()
	})
}

function habilitarBtnEliminarHorarioReserva(){
	$(".btn-eliminar-horario").click(function(){
		eliminarHorarioReserva(this)
	})
}

function habilitarBtnRegistrarHorarioReserva(){
	$("#registrar-horario-reserva").click(function(){
		registrarHorarioReserva()
	})
}

function confirmarEliminarHorario(){
	let row = $("table tbody tr.selected");
	
	if(!row){
		toastr["warning"]("Debe seleccionar una fila a eliminar.", "Validación" )
		//alert("Debe seleccionar una fila a eliminar.")
		return;
	}
	
	let idHorario = $(row).attr("data-id-horario")
	
	let data = {
		idHorario: idHorario
	}
	
	$.ajax({
        type: 'DELETE',
        url: '/api/horario-reserva/delete',
        data: data,
        success: function(response) {
			if(!response.success){
				//alert(response.message)
				toastr["error"](response.message, "Error" )
				return;				
			}
			
            location.reload();
        },
        error: function (jqXHR) {
			console.log(jqXHR)
			
			if(jqXHR.status == 401 || jqXHR.status == 403){
				toastr["error"]( "No esta autorizado a realizar esta acción","Error"  )
				return
			}
			
			toastr["error"]( jqXHR.status, "Error"  )
        }
	});
}

function eliminarHorarioReserva(e){
	$("table tbody tr").removeClass("selected")
	
	$(e.closest("tr")).toggleClass("selected");
}

function registrarHorarioReserva(){
	let fecha = $("#fecha-reserva").val();
	let horario = $("#horario-reserva").val();
	let mesas = $("#mesas-reserva").val();
	
	let data = {
        fecha: fecha,
        horario: horario,
        numeroMesas: mesas
    };
	
	$.ajax({
        type: 'POST',
        url: '/api/horario-reserva/create',
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(response) {
			if(!response.success){
				toastr["error"](response.message, "Error" )
				return;				
			}
			
            location.reload();
        },
        error: function (jqXHR) {
			console.log(jqXHR)
			
			if(jqXHR.status == 401 || jqXHR.status == 403){
				toastr["error"]( "No esta autorizado a realizar esta acción","Error"  )
				return
			}
			
			toastr["error"]( jqXHR.status, "Error"  )
        }
	});
}