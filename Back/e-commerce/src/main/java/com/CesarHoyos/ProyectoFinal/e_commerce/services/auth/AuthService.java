package com.CesarHoyos.ProyectoFinal.e_commerce.services.auth;

import com.CesarHoyos.ProyectoFinal.e_commerce.dto.SignupRequest;
import com.CesarHoyos.ProyectoFinal.e_commerce.dto.UserDto;

public interface AuthService {
	
	// Método para crear un nuevo usuario
	UserDto createUser(SignupRequest signupRequest);
	
	// Método para verificar si hay un usuario con el correo electrónico dado
	Boolean hasUserWithEmail(String email);
}