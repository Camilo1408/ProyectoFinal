package com.CesarHoyos.ProyectoFinal.e_commerce.dto;

import lombok.Data;

@Data
public class AddProductInCartDto {
	
	private Long userId;
	
	private Long productId;
}
