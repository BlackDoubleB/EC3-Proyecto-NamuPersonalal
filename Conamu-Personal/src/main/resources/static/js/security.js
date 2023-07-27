export class Security{
	obtenerTokenDesdeLocalStorage(){
		if(!this.validarlocalStorage()){
			//alert("El navegador no soporta LocalStorage.")
			toastr["warning"]( "El navegador no soporta LocalStorage.","Validación"  )
			return null
		}
		
		const token = localStorage.getItem("at")
		
		if(!token){
			return null
		}
		
		return "Bearer " + token;
	}
	
	validarlocalStorage(){
		var s = new Security()
		s.obtenerTokenDesdeLocalStorage
		return localStorage != null
	}
}