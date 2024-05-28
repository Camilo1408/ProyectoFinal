package com.CesarHoyos.ProyectoFinal.e_commerce.controller.costumer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CesarHoyos.ProyectoFinal.e_commerce.dto.AddProductInCartDto;
import com.CesarHoyos.ProyectoFinal.e_commerce.dto.OrderDto;
import com.CesarHoyos.ProyectoFinal.e_commerce.services.costumer.cart.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CartController {
	
	private final CartService cartService;
	
	// Método POST para agregar un producto al carrito
	@PostMapping("/cart")
	public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCartDto addProductInCartDto){
		// Se llama al servicio para agregar un producto al carrito utilizando los datos proporcionados
		return cartService.addProductToCart(addProductInCartDto);
	}
	
	// Método GET para obtener el carrito por ID de usuario
	@GetMapping("/cart/{userId}")
	public ResponseEntity<?> getCartByUserId(@PathVariable Long userId){
		// Se llama al servicio para obtener el carrito del usuario con el ID proporcionado
		OrderDto orderDto = cartService.getCartByUserId(userId);
		// Se devuelve una respuesta con el estado OK (200) y el objeto de pedido (carrito) en el cuerpo de la respuesta
		return ResponseEntity.status(HttpStatus.OK).body(orderDto);
	}
}
