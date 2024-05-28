package com.CesarHoyos.ProyectoFinal.e_commerce.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Importa las anotaciones y clases necesarias para definir el filtro y su orden de ejecución
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter{

	// Inyecta la URL de la aplicación cliente desde el archivo de configuración
	@Value("${app.client.url}")
	private String clientAppUrl="";

	// Constructor vacío
	public SimpleCorsFilter() {
 }

 // Método principal del filtro que se ejecuta en cada solicitud
 @Override
 public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
     // Convierte las solicitudes y respuestas genéricas a sus tipos HTTP específicos
     HttpServletResponse response = (HttpServletResponse) res;
     HttpServletRequest request = (HttpServletRequest) req;
     
     // Obtiene el valor del encabezado "origin" de la solicitud
     String originHeader = request.getHeader("origin");
     
     // Configura los encabezados de respuesta para permitir CORS (Cross-Origin Resource Sharing)
     response.setHeader("Access-Control-Allow-Origin", originHeader); // Permite el origen de la solicitud
     response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE"); // Métodos permitidos
     response.setHeader("Access-Control-Max-Age", "3600"); // Tiempo de vida de las respuestas preflight en segundos
     response.setHeader("Access-Control-Allow-Headers", "*"); // Permite todos los encabezados

     // Si la solicitud es un preflight (método OPTIONS), responde con OK y no sigue el filtro
     if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
         response.setStatus(HttpServletResponse.SC_OK);
     } else {
         // Si no, continúa con el siguiente filtro en la cadena
         chain.doFilter(req, res);
     }
 }

 // Método de inicialización del filtro (no se utiliza en este caso)
 @Override
 public void init(FilterConfig filterConfig) {
 }

 // Método de destrucción del filtro (opcional, no incluido)
 @Override
 public void destroy() {
 }
}

