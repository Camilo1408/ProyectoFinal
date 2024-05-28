package com.CesarHoyos.ProyectoFinal.e_commerce.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CesarHoyos.ProyectoFinal.e_commerce.dto.CategoryDto;
import com.CesarHoyos.ProyectoFinal.e_commerce.model.Category;
import com.CesarHoyos.ProyectoFinal.e_commerce.services.admin.category.CategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminCategoryController {

	// Inyección de dependencia del servicio de categoría
	private final CategoryService categoryService;
	
	// Método POST para crear una nueva categoría
	@PostMapping("category")
	public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto){
		// Se llama al servicio para crear la categoría utilizando los datos proporcionados en el cuerpo de la solicitud
		Category category = categoryService.createCategory(categoryDto);
		// Se devuelve una respuesta con el estado ACCEPTED (202) y el objeto de categoría creado en el cuerpo de la respuesta
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(category);
	}
	
	// Método GET para obtener todas las categorías
	@GetMapping("categories")
	public ResponseEntity<List<Category>> getAllCategories(){
		// Se llama al servicio para obtener todas las categorías
		// y se devuelve una respuesta con el estado OK (200) y la lista de categorías en el cuerpo de la respuesta
		return ResponseEntity.ok(categoryService.getAllCategories());
	}
}