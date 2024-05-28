package com.CesarHoyos.ProyectoFinal.e_commerce.services.admin.category;

import java.util.List;

import org.springframework.stereotype.Service;

import com.CesarHoyos.ProyectoFinal.e_commerce.dto.CategoryDto;
import com.CesarHoyos.ProyectoFinal.e_commerce.model.Category;
import com.CesarHoyos.ProyectoFinal.e_commerce.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	
	private final CategoryRepository categoryRepository; // Repositorio para operaciones de categorías
	
	// Método para crear una nueva categoría
	public Category createCategory(CategoryDto categoryDto) {
		// Se crea un nuevo objeto de categoría utilizando los datos del DTO proporcionado
		Category category = new Category();
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		
		// Se guarda la nueva categoría en la base de datos y se devuelve
		return categoryRepository.save(category);
	}
	
	// Método para obtener todas las categorías
	public List<Category> getAllCategories(){
		// Se obtienen todas las categorías desde el repositorio y se devuelven
		return categoryRepository.findAll();
	}
}
