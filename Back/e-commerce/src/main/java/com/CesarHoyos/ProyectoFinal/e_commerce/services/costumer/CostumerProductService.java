package com.CesarHoyos.ProyectoFinal.e_commerce.services.costumer;

import java.util.List;

import com.CesarHoyos.ProyectoFinal.e_commerce.dto.ProductDto;

public interface CostumerProductService {
	
	// Método para obtener todos los productos disponibles
	List<ProductDto> getAllProducts();
	
	// Método para buscar productos por título
	List<ProductDto> searchProductByTitle(String title);
}
