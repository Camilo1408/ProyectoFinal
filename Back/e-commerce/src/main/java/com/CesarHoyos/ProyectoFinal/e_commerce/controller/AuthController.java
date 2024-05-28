package com.CesarHoyos.ProyectoFinal.e_commerce.controller;

import java.io.IOException;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.CesarHoyos.ProyectoFinal.e_commerce.dto.AuthenticationRequest;
import com.CesarHoyos.ProyectoFinal.e_commerce.dto.SignupRequest;
import com.CesarHoyos.ProyectoFinal.e_commerce.dto.UserDto;
import com.CesarHoyos.ProyectoFinal.e_commerce.model.User;
import com.CesarHoyos.ProyectoFinal.e_commerce.repository.UserRepository;
import com.CesarHoyos.ProyectoFinal.e_commerce.services.auth.AuthService;
import com.CesarHoyos.ProyectoFinal.e_commerce.utils.JwtUtil;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

//Indica que esta clase es un controlador REST y que se deben inyectar sus dependencias requeridas automáticamente
@RestController
@RequiredArgsConstructor
public class AuthController {
	
	// Inyecta el gestor de autenticación
	private final AuthenticationManager authenticationManager;
	
	// Inyecta el servicio de detalles de usuario
	private final UserDetailsService userDetailsService;
	
	// Inyecta el repositorio de usuarios
	private final UserRepository userRepository;
	
	// Inyecta la utilidad JWT para generar tokens
	private final JwtUtil jwtUtil;
	
	// Define constantes para el prefijo del token y el nombre del encabezado de autorización
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	
	// Inyecta el servicio de autenticación personalizado
	private final AuthService authService;
	
	// Método que maneja las solicitudes POST para autenticar a un usuario
	@PostMapping("/authenticate")
	public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws IOException, JSONException {
		
		try {
			// Autentica al usuario utilizando el nombre de usuario y la contraseña proporcionados
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
					authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			// Lanza una excepción si las credenciales son incorrectas
			throw new BadCredentialsException("Incorrect username or password");
		}
		
		// Carga los detalles del usuario desde el servicio de detalles de usuario
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		// Busca el usuario en el repositorio utilizando su correo electrónico
		Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
		// Genera un token JWT para el usuario autenticado
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());
		
		if(optionalUser.isPresent()) {
			// Si el usuario existe, escribe su ID y rol en la respuesta
			response.getWriter().write(new JSONObject()
					.put("userId", optionalUser.get().getId())
					.put("role", optionalUser.get().getRole())
					.toString()
					);
			
			// Configura los encabezados de la respuesta para permitir la exposición del encabezado de autorización
			response.addHeader("Access-Control-Expose-Headers", "Authorization");
			// Configura los encabezados permitidos para solicitudes CORS
			response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, " + 
					"X-Requested-With, Content-Type, Accept, X-Custom-header");
			// Añade el token JWT al encabezado de autorización de la respuesta
			response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
		}
	}
	
	// Método que maneja las solicitudes POST para registrar un nuevo usuario
	@PostMapping("/sign-up")
	public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
		// Verifica si ya existe un usuario con el correo electrónico proporcionado
		if(authService.hasUserWithEmail(signupRequest.getEmail())) {
			// Si el usuario ya existe, devuelve una respuesta con el estado NOT_ACCEPTABLE
			return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
		}
		
		// Crea un nuevo usuario utilizando los datos del registro
		UserDto userDto = authService.createUser(signupRequest);
		// Devuelve la información del nuevo usuario con el estado OK
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}
}








