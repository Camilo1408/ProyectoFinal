package com.CesarHoyos.ProyectoFinal.e_commerce.model;

import com.CesarHoyos.ProyectoFinal.e_commerce.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
	
	/*
	Se genera la tabla de usuarios
	con sus valores principales.
	*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	private String password;
	private String name;
	private UserRole role;
	
	@Lob
	@Column(columnDefinition = "longblob")
	private byte[] img;
}
