package com.stockgain.api.config;

/**
 * JWTUTIL.JAVA
 * 
 * Utilidad para manejo de tokens JWT (JSON Web Tokens) en StockGain
 * 
 * Descripción:
 * Esta clase proporciona funcionalidades para generar, validar y extraer información
 * de tokens JWT utilizados para la autenticación en la aplicación StockGain.
 * Los tokens incluyen información del usuario y tienen una duración limitada.
 * 
 * Características principales:
 * - Generación de tokens JWT con información del usuario
 * - Validación de tokens (firma y expiración)
 * - Extracción de claims (información) del token
 * - Verificación de expiración
 * - Firma segura con clave HMAC
 * 
 * Configuración del token:
 * - Algoritmo: HMAC SHA-256
 * - Tiempo de expiración: 10 horas
 * - Claims incluidos: nombre, id_usuario, email (subject)
 * - Clave secreta: Cadena larga y segura
 * 
 * Seguridad:
 * - La clave secreta debe ser mantenida segura
 * - Los tokens incluyen timestamp de emisión y expiración
 * - Validación de firma para prevenir manipulación
 * 
 * @author StockGain Team
 * @version 1.0
 */

import com.stockgain.api.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
public class JwtUtil {
    
    // Clave secreta para firmar los tokens JWT (debe ser larga y segura)
    private final String SECRET_KEY_STRING = "esta-es-una-clave-secreta-muy-larga-y-segura-para-mi-proyecto-final-stockgain-2025-debe-ser-larga";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));
    
    // Tiempo de expiración del token: 10 horas
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;
    
    /**
     * Genera un token JWT para un usuario
     * 
     * @param usuario objeto Usuario con la información a incluir en el token
     * @return String token JWT generado
     */
    public String generateToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        // Incluir información del usuario en el token
        claims.put("nombre", usuario.getNombre());
        claims.put("id", usuario.getId_usuario());
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getEmail()) // Email como subject principal
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Fecha de expiración
                .signWith(key) // Firmar con la clave secreta
                .compact();
    }
    
    /**
     * Valida un token JWT verificando la firma y expiración
     * 
     * @param token token JWT a validar
     * @param userEmail email del usuario para verificar
     * @return Boolean true si el token es válido, false en caso contrario
     */
    public Boolean validateToken(String token, String userEmail) {
        final String username = extractUsername(token);
        return (username.equals(userEmail) && !isTokenExpired(token));
    }
    
    /**
     * Extrae el username (email) del token JWT
     * 
     * @param token token JWT
     * @return String username extraído del token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    /**
     * Extrae la fecha de expiración del token JWT
     * 
     * @param token token JWT
     * @return Date fecha de expiración del token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    /**
     * Extrae un claim específico del token JWT
     * 
     * @param <T> tipo del claim a extraer
     * @param token token JWT
     * @param claimsResolver función para resolver el claim
     * @return T valor del claim extraído
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    /**
     * Extrae todos los claims del token JWT
     * 
     * @param token token JWT
     * @return Claims todos los claims del token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
    
    /**
     * Verifica si un token JWT ha expirado
     * 
     * @param token token JWT a verificar
     * @return Boolean true si ha expirado, false si aún es válido
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}