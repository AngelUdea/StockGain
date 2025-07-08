package com.stockgain.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import static org.springframework.security.config.Customizer.withDefaults;

/**
 * SECURITYCONFIG.JAVA
 * 
 * Configuración de seguridad para la aplicación StockGain
 * 
 * Descripción:
 * Esta clase configura la seguridad de la aplicación utilizando Spring
 * Security.
 * Define las políticas de autenticación, autorización, CORS y manejo de
 * sesiones.
 * Implementa autenticación basada en JWT (JSON Web Tokens) sin estado.
 * 
 * Características principales:
 * - Autenticación JWT sin estado (stateless)
 * - Configuración CORS para permitir requests desde frontend
 * - Cifrado de contraseñas con BCrypt
 * - Filtro personalizado para validación de tokens JWT
 * - Endpoints públicos y protegidos
 * 
 * Configuración de seguridad:
 * - Endpoints públicos: /api/auth/** (login, registro)
 * - Endpoints protegidos: Todos los demás requieren autenticación
 * - Política de sesiones: STATELESS (sin sesiones de servidor)
 * - CSRF: Deshabilitado (apropiado para APIs REST)
 * 
 * CORS configurado para:
 * - Orígenes permitidos: http://127.0.0.1:5500, http://localhost:5500
 * - Métodos permitidos: GET, POST, PUT, DELETE, OPTIONS
 * - Headers permitidos: Authorization, Content-Type, X-Requested-With, Accept
 * 
 * @author StockGain Team
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Inyección del filtro JWT personalizado
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * Bean para el encoder de contraseñas BCrypt
     * 
     * @return BCryptPasswordEncoder configurado
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuración principal de la cadena de filtros de seguridad
     * 
     * @param http objeto HttpSecurity para configurar
     * @return SecurityFilterChain configurada
     * @throws Exception si hay errores en la configuración
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Habilitar CORS con configuración personalizada
                .cors(withDefaults())
                // Deshabilitar CSRF ya que usamos JWT
                .csrf(AbstractHttpConfigurer::disable)
                // Deshabilitar form login y basic auth
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // Configurar sesiones como STATELESS para JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configurar autorización de endpoints
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/price/**").permitAll()
                        .anyRequest().authenticated() // Todas las demás peticiones requieren autenticación
                )

                // Añadir filtro JWT antes del filtro de autenticación estándar
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Configuración de CORS (Cross-Origin Resource Sharing)
     * 
     * @return CorsConfigurationSource configurada para desarrollo
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Orígenes permitidos (frontend en desarrollo)
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500", "http://localhost:5500"));
        // Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Headers permitidos
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}