$(document).ready(function() {
	habilitarBtnIngresar()
});

function habilitarBtnIngresar(){
	$("#btn-ingresar").click(function(e){
		procesarLogin(e)
	})
}

function procesarLogin(e){
	e.preventDefault()
	
	let username = $("#username").val()
	let password = $("#password").val()
	
	const data = {
		username,
		password
	}
	
	$.ajax({
		type: 'POST',
		url: '/login',
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(e, s, request) {
			let token = request.getResponseHeader("authorization")
			//Libreria toastr
			if (!token || !token.startsWith("Bearer ")){
				//alert("No ha sido posible autenticar al usuario ingresado.")
				toastr["error"]( "No ha sido posible autenticar al usuario ingresado.","Error"  )
				return;
			}
			
			if(!localStorage){
				//alert("El navegador no soporta LocalStorage.")
				toastr["warning"]( "El navegador no soporta LocalStorage.","Validación"  )
				return;
			}
			
			localStorage.setItem("at", token.replace("Bearer ", ""))
			
			location.href = "/reservas";
		},
		error: function(jqXHR) {
			console.log(jqXHR)
			
			if(jqXHR.status == 401 || jqXHR.status == 403){
				toastr["error"]( "Usuario y/o Contraseña incorrectas","Error"  )
				//alert("Usuario y/o Contraseña incorrectas")
				return
			}
			
			//alert('Error: ' + jqXHR.status)
			toastr["error"]( jqXHR.status, "Error"  )
		}
	});
}
