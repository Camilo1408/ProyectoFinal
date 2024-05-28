package com.CesarHoyos.ProyectoFinal.e_commerce.services.costumer.cart;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.CesarHoyos.ProyectoFinal.e_commerce.dto.AddProductInCartDto;
import com.CesarHoyos.ProyectoFinal.e_commerce.dto.CartItemsDto;
import com.CesarHoyos.ProyectoFinal.e_commerce.dto.OrderDto;
import com.CesarHoyos.ProyectoFinal.e_commerce.enums.OrderStatus;
import com.CesarHoyos.ProyectoFinal.e_commerce.model.CartItems;
import com.CesarHoyos.ProyectoFinal.e_commerce.model.Order;
import com.CesarHoyos.ProyectoFinal.e_commerce.model.Product;
import com.CesarHoyos.ProyectoFinal.e_commerce.model.User;
import com.CesarHoyos.ProyectoFinal.e_commerce.repository.CartItemsRepository;
import com.CesarHoyos.ProyectoFinal.e_commerce.repository.OrderRepository;
import com.CesarHoyos.ProyectoFinal.e_commerce.repository.ProductRepository;
import com.CesarHoyos.ProyectoFinal.e_commerce.repository.UserRepository;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private OrderRepository orderRepository; // Repositorio para operaciones de pedidos
	
	@Autowired
	private UserRepository userRepository; // Repositorio para operaciones de usuarios
	
	@Autowired
	private CartItemsRepository cartItemsRepository; // Repositorio para operaciones de elementos del carrito
	
	@Autowired
	private ProductRepository productRepository; // Repositorio para operaciones de productos
	
	// Método para agregar un producto al carrito
	public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto){
		// Se busca el pedido activo del usuario
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
		// Se busca si el producto ya está en el carrito
		Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
				addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());
		
		// Si el producto ya está en el carrito, se devuelve un error de conflicto
		if(optionalCartItems.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		} else {
			// Se busca el producto y el usuario
			Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
			Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());	
			
			// Si el producto y el usuario existen
			if (optionalProduct.isPresent() && optionalUser.isPresent()) {
				// Se crea un nuevo elemento de carrito
				CartItems cart = new CartItems();
				cart.setProduct(optionalProduct.get());
				cart.setPrice(optionalProduct.get().getPrice());
				cart.setQuantity(1L);
				cart.setUser(optionalUser.get());
				cart.setOrder(activeOrder);
				
				// Se guarda el nuevo elemento de carrito en la base de datos
				CartItems updatedCart = cartItemsRepository.save(cart);
				
				// Se actualiza el pedido activo con el nuevo elemento de carrito
				activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cart.getPrice());
				activeOrder.setAmount(activeOrder.getAmount() + cart.getPrice());
				activeOrder.getCartItems().add(cart);
				
				// Se guarda el pedido actualizado en la base de datos
				orderRepository.save(activeOrder);
				
				// Se devuelve una respuesta con el estado CREATED (201) y el ID del elemento de carrito creado
				return ResponseEntity.status(HttpStatus.CREATED).body(cart.getId());
			} else {
				// Si el producto o el usuario no existen, se devuelve un error 404
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
			}
		}
	}
	
	// Método para obtener el carrito por ID de usuario
	public OrderDto getCartByUserId(Long userId) {
		// Se busca el pedido activo del usuario
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
		// Se mapean los elementos del carrito a DTOs
		List<CartItemsDto> cartItemsDtoList = activeOrder.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());
				
		// Se crea un DTO para el pedido con los detalles del carrito y se devuelve
		OrderDto orderDto = new OrderDto();
		orderDto.setAmount(activeOrder.getAmount());
		orderDto.setId(activeOrder.getId());
		orderDto.setOrderStatus(activeOrder.getOrderStatus());
		orderDto.setDiscount(activeOrder.getDiscount());
		orderDto.setTotalAmount(activeOrder.getTotalAmount());
		orderDto.setCartItems(cartItemsDtoList);
		
		return orderDto;
	}	
}
