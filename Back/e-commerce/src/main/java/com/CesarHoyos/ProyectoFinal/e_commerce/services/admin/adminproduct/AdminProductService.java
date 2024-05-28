package com.CesarHoyos.ProyectoFinal.e_commerce.services.admin.adminproduct;

import java.io.IOException;
import java.util.List;

import com.CesarHoyos.ProyectoFinal.e_commerce.dto.ProductDto;

public interface AdminProductService {
	
	// Método para agregar un nuevo producto
	ProductDto addProduct(ProductDto productDto) throws IOException;
	
	// Método para obtener todos los productos
	List<ProductDto> getAllProducts();
	
	// Método para obtener productos por nombre
	List<ProductDto> getAllProductsByName(String name);
	
	// Método para eliminar un producto por su ID
	boolean deleteProduct(Long id);
}
