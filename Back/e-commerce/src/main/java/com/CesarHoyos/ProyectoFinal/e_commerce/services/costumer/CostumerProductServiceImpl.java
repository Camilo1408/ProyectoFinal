package com.CesarHoyos.ProyectoFinal.e_commerce.services.costumer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.CesarHoyos.ProyectoFinal.e_commerce.dto.ProductDto;
import com.CesarHoyos.ProyectoFinal.e_commerce.model.Product;
import com.CesarHoyos.ProyectoFinal.e_commerce.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CostumerProductServiceImpl implements CostumerProductService{
	
	private final ProductRepository productRepository; // Repositorio para operaciones de productos
	
	// Método para obtener todos los productos disponibles
	public List<ProductDto> getAllProducts(){
		// Se obtienen todos los productos desde el repositorio
		List<Product> products = productRepository.findAll();
		// Se mapean los productos a DTOs y se devuelven en una lista
		return products.stream().map(Product::getDto).collect(Collectors.toList());
	}
	
	// Método para buscar productos por título
	public List<ProductDto> searchProductByTitle(String name){
		// Se buscan los productos que contienen el título especificado
		List<Product> products = productRepository.findAllByNameContaining(name);
		// Se mapean los productos a DTOs y se devuelven en una lista
		return products.stream().map(Product::getDto).collect(Collectors.toList());
	}
}
