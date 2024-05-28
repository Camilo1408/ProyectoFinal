package com.CesarHoyos.ProyectoFinal.e_commerce.controller.costumer;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CesarHoyos.ProyectoFinal.e_commerce.dto.ProductDto;
import com.CesarHoyos.ProyectoFinal.e_commerce.services.costumer.CostumerProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/costumer")
@RequiredArgsConstructor
public class CostumerProductController {
	
	private final CostumerProductService costumerProductService;
	
	// Método GET para obtener todos los productos disponibles
	@GetMapping("/products")
	public ResponseEntity<List<ProductDto>> getAllProducts(){
		// Se llama al servicio para obtener todos los productos disponibles
		List<ProductDto> productDtos = costumerProductService.getAllProducts();
		// Se devuelve una respuesta con el estado OK (200) y la lista de productos en el cuerpo de la respuesta
		return ResponseEntity.ok(productDtos);
	}
	
	// Método GET para buscar productos por nombre
	@GetMapping("/search/{name}")
	public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name){
		// Se llama al servicio para buscar productos por título/nombre
		List<ProductDto> productDtos = costumerProductService.searchProductByTitle(name);
		// Se devuelve una respuesta con el estado OK (200) y la lista de productos encontrados en el cuerpo de la respuesta
		return ResponseEntity.ok(productDtos);
	}
}
