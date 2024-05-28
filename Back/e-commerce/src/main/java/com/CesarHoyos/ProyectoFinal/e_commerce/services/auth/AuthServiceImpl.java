package com.CesarHoyos.ProyectoFinal.e_commerce.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.CesarHoyos.ProyectoFinal.e_commerce.dto.SignupRequest;
import com.CesarHoyos.ProyectoFinal.e_commerce.dto.UserDto;
import com.CesarHoyos.ProyectoFinal.e_commerce.enums.OrderStatus;
import com.CesarHoyos.ProyectoFinal.e_commerce.enums.UserRole;
import com.CesarHoyos.ProyectoFinal.e_commerce.model.Order;
import com.CesarHoyos.ProyectoFinal.e_commerce.model.User;
import com.CesarHoyos.ProyectoFinal.e_commerce.repository.OrderRepository;
import com.CesarHoyos.ProyectoFinal.e_commerce.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private  UserRepository userRepository; // Repositorio para operaciones de usuarios
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; // Codificador de contraseñas
	
	@Autowired
	private OrderRepository orderRepository; // Repositorio para operaciones de pedidos
	
	// Método para crear un nuevo usuario
	public UserDto createUser(SignupRequest signupRequest) {
		// Se crea un nuevo objeto de usuario utilizando los datos del SignupRequest
		User user = new User();
		user.setEmail(signupRequest.getEmail());
		user.setName(signupRequest.getName());
		// Se codifica la contraseña antes de almacenarla
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setRole(UserRole.COSTUMER);
		// Se guarda el nuevo usuario en la base de datos
		User createdUser = userRepository.save(user);
		
		// Se crea un nuevo pedido para el usuario
		Order order = new Order();
		order.setAmount(0L);
		order.setTotalAmount(0L);
		order.setDiscount(0L);
		order.setUser(createdUser);
		order.setOrderStatus(OrderStatus.Pending);
		orderRepository.save(order);
		
		// Se crea un DTO para el usuario creado y se devuelve
		UserDto userDto = new UserDto();
		userDto.setId(createdUser.getId());
		return userDto;
	}
	
	// Método para verificar si hay un usuario con el correo electrónico dado
	public Boolean hasUserWithEmail(String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}
	
	// Método para crear una cuenta de administrador durante la inicialización
	@PostConstruct
	public void createAdminAccount() {
		// Se verifica si ya existe una cuenta de administrador
		User adminAccount = userRepository.findByRole(UserRole.ADMIN);
		// Si no existe una cuenta de administrador, se crea una
		if(null == adminAccount) {
			User user = new User();
			user.setEmail("admin@test.com");
			user.setName("admin");
			user.setRole(UserRole.ADMIN);
			// Se codifica la contraseña antes de almacenarla
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}
	}
}









