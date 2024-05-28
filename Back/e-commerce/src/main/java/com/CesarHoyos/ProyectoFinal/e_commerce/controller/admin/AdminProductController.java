package com.CesarHoyos.ProyectoFinal.e_commerce.controller.admin;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CesarHoyos.ProyectoFinal.e_commerce.dto.ProductDto;
import com.CesarHoyos.ProyectoFinal.e_commerce.services.admin.adminproduct.AdminProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminProductController {
	
	private final AdminProductService adminProductService;
	
	// Método POST para agregar un nuevo producto
	@PostMapping("/product")
	public ResponseEntity<ProductDto> addProduct(@ModelAttribute ProductDto productDto) throws IOException{
		// Se llama al servicio para agregar un nuevo producto utilizando los datos proporcionados
		ProductDto productDto1 = adminProductService.addProduct(productDto);
		// Se devuelve una respuesta con el estado CREATED (201) y el objeto de producto creado en el cuerpo de la respuesta
		return ResponseEntity.status(HttpStatus.CREATED).body(productDto1);
	}
	
	// Método GET para obtener todos los productos
	@GetMapping("/products")
	public ResponseEntity<List<ProductDto>> getAllProducts(){
		// Se llama al servicio para obtener todos los productos
		List<ProductDto> productDtos = adminProductService.getAllProducts();
		// Se devuelve una respuesta con el estado OK (200) y la lista de productos en el cuerpo de la respuesta
		return ResponseEntity.ok(productDtos);
	}
	
	// Método GET para obtener todos los productos por nombre
	@GetMapping("/search/{name}")
	public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name){
		// Se llama al servicio para obtener todos los productos con un nombre específico
		List<ProductDto> productDtos = adminProductService.getAllProductsByName(name);
		// Se devuelve una respuesta con el estado OK (200) y la lista de productos en el cuerpo de la respuesta
		return ResponseEntity.ok(productDtos);
	}
	
	// Método DELETE para eliminar un producto por su ID
	@DeleteMapping("/product/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
		// Se llama al servicio para eliminar un producto con el ID especificado
		boolean deleted = adminProductService.deleteProduct(productId);
		// Si se elimina correctamente, se devuelve una respuesta con el estado NO_CONTENT (204)
		if(deleted) {
			return ResponseEntity.noContent().build();		
		}
		// Si no se encuentra el producto, se devuelve una respuesta con el estado NOT_FOUND (404)
		return ResponseEntity.notFound().build();
	}
}




