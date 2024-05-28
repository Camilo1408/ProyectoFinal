package com.CesarHoyos.ProyectoFinal.e_commerce.services.admin.category;

import java.util.List;

import com.CesarHoyos.ProyectoFinal.e_commerce.dto.CategoryDto;
import com.CesarHoyos.ProyectoFinal.e_commerce.model.Category;

public interface CategoryService {
	
	// Método para crear una nueva categoría
	Category createCategory(CategoryDto categoryDto);
	
	// Método para obtener todas las categorías
	List<Category> getAllCategories();
}