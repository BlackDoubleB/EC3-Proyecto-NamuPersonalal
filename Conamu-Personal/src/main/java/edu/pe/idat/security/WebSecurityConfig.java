package edu.pe.idat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	@Autowired
	JwtTokenUtils jwtUtils;
	
	
	// Es una clase personalizada que implementa la interfaz UserDetailsService de Spring Security.
    //Esta clase se utiliza para cargar los detalles del usuario desde la base de datos o cualquier otro origen de datos.
	@Autowired
	UserDetailServiceImpl userDetailsService;
	
	
	@Autowired
	JwtAuthorizationFilter jwtAuthorizationFilter;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
	
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
		jwtAuthenticationFilter.setAuthenticationManager(authManager);
		
		
		
		//Se establece la URL de inicio de sesión ("/login") para el filtro JwtAuthenticationFilter. 
		//Esto indica que cuando una solicitud se realice a esta URL, el filtro procesará la autenticación.
		//Este código configura una cadena de filtros de seguridad para gestionar la autenticación y autorización en la aplicación.
		jwtAuthenticationFilter.setFilterProcessesUrl("/login");
		
		return http
				.csrf(config -> config.disable())
				.authorizeHttpRequests(auth -> {
					auth.anyRequest().permitAll();
				})
				.sessionManagement(session -> {
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				})
				.addFilter(jwtAuthenticationFilter)
				.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	
	
	
	//Se define un bean passwordEncoder que utiliza el algoritmo BCryptPasswordEncoder
	// Esto para codificar las contraseñas de los usuarios antes de almacenarlas en la base de datos.
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	
	//Se define un bean authManager que configura el AuthenticationManager con el UserDetailsService personalizado y el codificador de contraseñas. 
	//Esto es necesario para manejar la autenticación en Spring Security.
	@Bean
	AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception{
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder)
				.and()
				.build();
	}
	
}
