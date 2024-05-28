package com.CesarHoyos.ProyectoFinal.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CesarHoyos.ProyectoFinal.e_commerce.enums.OrderStatus;
import com.CesarHoyos.ProyectoFinal.e_commerce.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	
	Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
}
