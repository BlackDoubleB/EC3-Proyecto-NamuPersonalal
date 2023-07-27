package edu.pe.idat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import edu.pe.idat.request.UsuarioCreateRequest;
import edu.pe.idat.service.TrabajadorService;

@SpringBootApplication
public class ConamuPersonalApplication {
	
	@Autowired
	TrabajadorService userService;
	
	
	//El método main es el punto de entrada de la aplicación Spring Boot. 
	//Aquí, se utiliza SpringApplication.run() para iniciar la aplicación y arrancar el contenedor de Spring.
	public static void main(String[] args) {
		SpringApplication.run(ConamuPersonalApplication.class, args);
	}
	
	//El método init() crea un usuario administrador si no existe en la base de datos.
	//Utiliza el servicio userService para buscar un usuario con el nombre de usuario "admin" (findOneByUsuarioOptional("admin")). 
	//Si no se encuentra un usuario con ese nombre de usuario, se crea un nuevo usuario administrador utilizando la clase UsuarioCreateRequest.
	//Luego se agrega al sistema llamando al método addPersonaTrabajador(user) del servicio userService.
	
	@Bean
	CommandLineRunner init() {
		return args -> {
			
			var usuario = userService.findOneByUsuarioOptional("admin");
			
			if(usuario.isPresent()) {
				return;
			}
			
			UsuarioCreateRequest user = new UsuarioCreateRequest();
			
			user.setContrasena("admin");
			user.setCorreo("test@idat.pe");
			user.setDni("79797979");
			user.setNombreCompletos("Blanca Blacido");
			user.setRol("3");
			user.setTelefono("951654321");
			user.setUsuario("admin");

			userService.addPersonaTrabajador(user);
		};
	}

}
