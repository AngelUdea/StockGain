package com.stockgain.api.config;

/**
 * JWTREQUESTFILTER.JAVA
 * 
 * Filtro de peticiones HTTP para validación de tokens JWT en StockGain
 * 
 * Descripción:
 * Este filtro intercepta todas las peticiones HTTP para validar tokens JWT
 * en el header Authorization. Si el token es válido, establece la autenticación
 * en el contexto de seguridad de Spring para la petición actual.
 * 
 * Funcionalidades principales:
 * - Intercepta peticiones HTTP antes de llegar a los controladores
 * - Extrae tokens JWT del header Authorization
 * - Valida tokens usando JwtUtil
 * - Establece autenticación en SecurityContext
 * - Maneja errores de tokens inválidos o expirados
 * 
 * Flujo de procesamiento:
 * 1. Extrae el header Authorization
 * 2. Verifica formato "Bearer [token]"
 * 3. Extrae el username del token
 * 4. Carga detalles del usuario
 * 5. Valida el token
 * 6. Establece autenticación si es válido
 * 7. Continúa con la cadena de filtros
 * 
 * Integración con Spring Security:
 * - Extiende OncePerRequestFilter para ejecución única por petición
 * - Utiliza UserDetailsService para cargar información del usuario
 * - Establece UsernamePasswordAuthenticationToken en SecurityContext
 * - Maneja detalles de autenticación web
 * 
 * Manejo de errores:
 * - Tokens malformados o expirados son capturados y loggeados
 * - Peticiones sin token válido continúan sin autenticación
 * - No interrumpe el flujo normal de la aplicación
 * 
 * @author StockGain Team
 * @version 1.0
 */

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    
    // Inyección de dependencias para servicios de autenticación
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * Método principal del filtro que procesa cada petición HTTP
     * 
     * @param request petición HTTP entrante
     * @param response respuesta HTTP saliente
     * @param chain cadena de filtros a continuar
     * @throws ServletException si hay error en el servlet
     * @throws IOException si hay error de entrada/salida
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        
        // Extraer el header Authorization de la petición
        final String authorizationHeader = request.getHeader("Authorization");
        
        String username = null;
        String jwt = null;
        
        // Verificar si el header contiene un token Bearer
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extraer el token removiendo el prefijo "Bearer "
            jwt = authorizationHeader.substring(7);
            try {
                // Extraer el username del token JWT
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                // Manejo de errores: token expirado, malformado, etc.
                System.out.println("Error al extraer username del token: " + e.getMessage());
            }
        }
        
        // Si hay username válido y no hay autenticación previa en el contexto
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // Cargar detalles del usuario usando el UserDetailsService
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            
            // Validar el token JWT
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                // Crear token de autenticación para Spring Security
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                
                // Establecer detalles de la petición web
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        
        // Continuar con la cadena de filtros
        chain.doFilter(request, response);
    }
}