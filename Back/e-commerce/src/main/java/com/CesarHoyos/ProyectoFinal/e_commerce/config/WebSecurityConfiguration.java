package com.CesarHoyos.ProyectoFinal.e_commerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.CesarHoyos.ProyectoFinal.e_commerce.filters.JwtRequestFilter;

import lombok.RequiredArgsConstructor;

//Anotaciones que indican que esta es una clase de configuración y habilita la seguridad web en la aplicación
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

 // Filtro personalizado para la autenticación JWT
 private final JwtRequestFilter authFilter;

 // Método que define la cadena de filtros de seguridad
 @Bean
 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     return http
             // Deshabilita la protección CSRF, ya que no es necesaria para las APIs REST
             .csrf(csrf -> csrf.disable())
             
             // Define las reglas de autorización para las solicitudes HTTP
             .authorizeHttpRequests(requests -> requests
                     // Permite el acceso público a las rutas especificadas
                     .requestMatchers("/authenticate", "/sign-up", "/order/**", 
                             "/api/admin/category", "api/admin/categories", "api/admin/product", 
                             "api/admin/products", "api/admin/search/{name}", "api/admin/product/{productId}", 
                             "api/costumer/products", "api/costumer/search/{name}", "api/costumer/cart", 
                             "api/costumer/cart/{userId}", "/swagger-ui/index.html")
                     .permitAll())
             
             // Requiere autenticación para cualquier otra solicitud que comience con "/api/"
             .authorizeHttpRequests(requests -> requests
                     .requestMatchers("/api/**")
                     .authenticated())
             
             // Configura la política de manejo de sesiones para que no mantenga estado (stateless)
             .sessionManagement(management -> management
                     .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
             
             // Añade el filtro JWT antes del filtro de autenticación de usuario y contraseña
             .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
             
             // Construye y devuelve la cadena de filtros de seguridad configurada
             .build();
 }

 // Define un bean para codificar contraseñas utilizando BCrypt
 @Bean
 PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder();
 }

 // Define un bean para gestionar la autenticación
 @Bean
 public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
     return config.getAuthenticationManager();
 }
}







