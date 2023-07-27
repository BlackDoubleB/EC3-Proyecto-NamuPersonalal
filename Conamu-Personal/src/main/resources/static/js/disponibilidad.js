$(document).ready(function() {
	setDefaultValues();
	
	habilitarInputFechaOnChange();
});

//configura los campos para agregar horarios a sus valores por defecto
function setDefaultValues(){
	let fechaInput = document.getElementById("fecha-reserva")
	
	fechaInput.value = dayjs().format("YYYY-MM-DD")
}

function habilitarInputFechaOnChange() {
	$("#fecha-reserva").change(function() {

		let fecha = $(this).val()

		let data = {
			fecha: fecha
		}

		$.ajax({
			type: 'GET',
			url: '/disponibilidad/table/' + fecha,
			data: data,
			success: function(response) {
				if (!response) {
					return;
				}

				$(".datagrid").html(response)
			},
			error: function(jqXHR) {
				console.log(jqXHR)
				
				if(jqXHR.status == 401 || jqXHR.status == 403){
					toastr["error"]( "No esta autorizado a realizar esta acción","Error"  )
					//alert("No esta autorizado a realizar esta acción")
					return
				}
				
				//alert('Error: ' + jqXHR.status)
				toastr["error"]( jqXHR.status, "Error"  )
			}
		});
	})
}