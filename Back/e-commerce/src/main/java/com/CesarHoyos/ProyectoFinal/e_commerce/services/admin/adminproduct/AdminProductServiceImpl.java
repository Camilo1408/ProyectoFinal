package com.CesarHoyos.ProyectoFinal.e_commerce.services.admin.adminproduct;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.CesarHoyos.ProyectoFinal.e_commerce.dto.ProductDto;
import com.CesarHoyos.ProyectoFinal.e_commerce.model.Category;
import com.CesarHoyos.ProyectoFinal.e_commerce.model.Product;
import com.CesarHoyos.ProyectoFinal.e_commerce.repository.CategoryRepository;
import com.CesarHoyos.ProyectoFinal.e_commerce.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService{
	
	private final ProductRepository productRepository; // Repositorio para operaciones de productos
	
	private final CategoryRepository categoryRepository; // Repositorio para operaciones de categorías
	
	// Método para agregar un nuevo producto
	public ProductDto addProduct(ProductDto productDto) throws IOException {
		// Se crea un nuevo objeto de producto utilizando los datos del DTO proporcionado
		Product product = new Product();
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setImg(productDto.getImg().getBytes());
		
		// Se busca la categoría correspondiente al ID proporcionado en el DTO
		Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow();
		
		// Se asigna la categoría al producto
		product.setCategory(category);
		
		// Se guarda el producto en la base de datos y se devuelve su DTO
		return productRepository.save(product).getDto();
	}
	
	// Método para obtener todos los productos
	public List<ProductDto> getAllProducts(){
		// Se obtienen todos los productos desde el repositorio
		List<Product> products = productRepository.findAll();
		// Se mapean los productos a DTOs y se devuelven en una lista
		return products.stream().map(Product::getDto).collect(Collectors.toList());
	}
	
	// Método para obtener productos por nombre
	public List<ProductDto> getAllProductsByName(String name){
		// Se buscan los productos que contienen el nombre proporcionado en su nombre
		List<Product> products = productRepository.findAllByNameContaining(name);
		// Se mapean los productos a DTOs y se devuelven en una lista
		return products.stream().map(Product::getDto).collect(Collectors.toList());
	}
	
	// Método para eliminar un producto por su ID
	public boolean deleteProduct(Long id) {
		// Se busca el producto por su ID
		Optional<Product> optionalProduct = productRepository.findById(id);
		// Si el producto existe, se elimina y se devuelve true, de lo contrario, se devuelve false
		if(optionalProduct.isPresent()) {
			productRepository.deleteById(id);
			return true;
		}
		return false;
	}
}







