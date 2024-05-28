package com.CesarHoyos.ProyectoFinal.e_commerce.filters;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.CesarHoyos.ProyectoFinal.e_commerce.services.jwt.UserDetailsServiceImpl;
import com.CesarHoyos.ProyectoFinal.e_commerce.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter{
	
	private final UserDetailsServiceImpl userDetailsService; // Servicio para cargar detalles del usuario
	
	private final JwtUtil jwtUtil; // Utilidad para operaciones relacionadas con JWT
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Se obtiene el encabezado de autorización de la solicitud
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		
		// Si el encabezado de autorización no es nulo y comienza con "Bearer "
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			// Se extrae el token JWT del encabezado
			token = authHeader.substring(7);
			// Se extrae el nombre de usuario del token JWT
			username = jwtUtil.extractUsername(token);
		}
		
		// Si se ha extraído un nombre de usuario del token y no hay autenticación en el contexto de seguridad actual
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// Se cargan los detalles del usuario utilizando el nombre de usuario extraído
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			// Si el token es válido para el usuario
			if(jwtUtil.validateToken(token, userDetails)) {
				// Se crea un objeto de autenticación con los detalles del usuario y se establece en el contexto de seguridad
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null);
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		
		// Se pasa la solicitud y la respuesta al siguiente filtro en la cadena de filtros
		filterChain.doFilter(request, response);
	}
}





