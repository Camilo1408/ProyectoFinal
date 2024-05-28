package com.CesarHoyos.ProyectoFinal.e_commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CesarHoyos.ProyectoFinal.e_commerce.enums.UserRole;
import com.CesarHoyos.ProyectoFinal.e_commerce.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findFirstByEmail(String email);
	
	User findByRole(UserRole userRole);
}
  