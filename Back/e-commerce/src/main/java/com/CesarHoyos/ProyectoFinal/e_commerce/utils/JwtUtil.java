package com.CesarHoyos.ProyectoFinal.e_commerce.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	// Clave secreta para firmar y verificar el token JWT
	public static final String SECRET ="7821EE48B038B7C45544E2410F28C70919C88652459B1CBCC58A989C43D03823";
	
	// Método para generar un token JWT para un usuario dado
	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return createToken(claims, userName);
	}

	// Método privado para crear un token JWT con las reclamaciones y el nombre de usuario dados
	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+10000*60*30)) // Token expira en 30 minutos
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	// Método privado para obtener la clave de firma
	private Key getSignKey() {
		byte[] keybytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keybytes);
	}
	
	// Método para extraer el nombre de usuario del token JWT
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// Método genérico para extraer una reclamación específica del token JWT
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// Método privado para extraer todas las reclamaciones del token JWT
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}
	
	// Método privado para verificar si el token JWT ha expirado
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Método para extraer la fecha de expiración del token JWT
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	// Método para validar el token JWT
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}




