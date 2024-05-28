package com.CesarHoyos.ProyectoFinal.e_commerce.services.costumer.cart;

import org.springframework.http.ResponseEntity;

import com.CesarHoyos.ProyectoFinal.e_commerce.dto.AddProductInCartDto;
import com.CesarHoyos.ProyectoFinal.e_commerce.dto.OrderDto;

public interface CartService {
	
	// Método para agregar un producto al carrito
	ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);
	
	// Método para obtener el carrito por ID de usuario
	OrderDto getCartByUserId(Long userId);
}