package com.CesarHoyos.ProyectoFinal.e_commerce.services.jwt;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.CesarHoyos.ProyectoFinal.e_commerce.model.User;
import com.CesarHoyos.ProyectoFinal.e_commerce.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository; // Repositorio para operaciones de usuarios
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Se busca un usuario por su correo electrónico en el repositorio de usuarios
		Optional<User> optionalUser = userRepository.findFirstByEmail(username);
		// Si no se encuentra ningún usuario con el correo electrónico dado, se lanza una excepción
		if (optionalUser.isEmpty()) throw new UsernameNotFoundException("Username not found", null);
		// Se crea y devuelve un objeto UserDetails utilizando la información del usuario encontrado
		return new org.springframework.security.core.userdetails.User(
			optionalUser.get().getEmail(),
			optionalUser.get().getPassword(),
			new ArrayList<>()
		);
	}
}
