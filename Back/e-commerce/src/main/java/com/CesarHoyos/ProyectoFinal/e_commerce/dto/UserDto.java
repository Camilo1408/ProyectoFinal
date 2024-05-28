package com.CesarHoyos.ProyectoFinal.e_commerce.dto;

import com.CesarHoyos.ProyectoFinal.e_commerce.enums.UserRole;

import lombok.Data;

@Data
public class UserDto {
	
	private Long id;
	private String email;
	private String name;
	private UserRole userRole;
}
