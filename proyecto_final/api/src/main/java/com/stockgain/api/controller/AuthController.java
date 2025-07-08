package com.stockgain.api.controller;

/**
 * AUTHCONTROLLER.JAVA
 * 
 * Controlador REST para autenticación de usuarios en StockGain
 * 
 * Descripción:
 * Este controlador maneja las operaciones de autenticación de la aplicación,
 * incluyendo el registro de nuevos usuarios y el inicio de sesión. Proporciona
 * endpoints públicos para que los usuarios puedan acceder a la plataforma.
 * 
 * Endpoints disponibles:
 * - POST /api/auth/registro - Registro de nuevos usuarios
 * - POST /api/auth/login - Inicio de sesión de usuarios existentes
 * 
 * Funcionalidades principales:
 * - Registro de usuarios con validación de email único
 * - Cifrado de contraseñas con BCrypt
 * - Generación de tokens JWT para autenticación
 * - Creación automática de portafolio por defecto
 * - Validación de credenciales en login
 * - Manejo de errores de autenticación
 * 
 * Seguridad:
 * - Contraseñas cifradas con BCrypt
 * - Tokens JWT con expiración
 * - Validación de credenciales
 * - Manejo seguro de errores sin revelar información sensible
 * 
 * Integración:
 * - Usa UsuarioRepository para persistencia de usuarios
 * - Usa PortafolioRepository para crear portafolio por defecto
 * - Utiliza JwtUtil para generación de tokens
 * - Integrado con Spring Security
 * 
 * @author StockGain Team
 * @version 1.0
 */

import com.stockgain.api.config.JwtUtil;
import com.stockgain.api.model.Portafolio;
import com.stockgain.api.model.Usuario;
import com.stockgain.api.repository.PortafolioRepository;
import com.stockgain.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    // Inyección de dependencias para repositories y servicios
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PortafolioRepository portafolioRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * Endpoint para registrar nuevos usuarios
     * 
     * @param payload mapa con datos del usuario (nombre, email, password)
     * @return ResponseEntity con el usuario creado o mensaje de error
     */
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Map<String, String> payload) {
        // Extraer datos del payload
        String nombre = payload.get("nombre");
        String email = payload.get("email");
        String password = payload.get("password");
        
        // Validar que el email no esté ya registrado
        if (usuarioRepository.findByEmail(email).isPresent()) {
            return new ResponseEntity<>("El correo electrónico ya está registrado.", HttpStatus.CONFLICT);
        }
        
        // Crear nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        // Cifrar contraseña antes de guardar
        nuevoUsuario.setContrasenaHash(bCryptPasswordEncoder.encode(password));
        
        // Guardar usuario en la base de datos
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        
        // Crear portafolio por defecto para el nuevo usuario
        Portafolio nuevoPortafolio = new Portafolio();
        nuevoPortafolio.setUsuario(usuarioGuardado);
        nuevoPortafolio.setNombrePortafolio("Mi Portafolio Principal");
        nuevoPortafolio.setDescripcion("Portafolio de inversiones principal.");
        nuevoPortafolio.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
        portafolioRepository.save(nuevoPortafolio);
        
        return new ResponseEntity<>(usuarioGuardado, HttpStatus.CREATED);
    }
    
    /**
     * Endpoint para inicio de sesión de usuarios
     * 
     * @param payload mapa con credenciales (email, password)
     * @return ResponseEntity con token JWT y datos del usuario o mensaje de error
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody Map<String, String> payload) {
        // Extraer credenciales del payload
        String email = payload.get("email");
        String password = payload.get("password");
        
        // Buscar usuario por email
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            return new ResponseEntity<>("Credenciales inválidas.", HttpStatus.UNAUTHORIZED);
        }
        
        Usuario usuario = usuarioOpt.get();
        
        // Verificar contraseña
        if (bCryptPasswordEncoder.matches(password, usuario.getContrasenaHash())) {
            // Generar token JWT para el usuario autenticado
            String token = jwtUtil.generateToken(usuario);
            
            // Preparar respuesta con token y datos del usuario
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", usuario);
            
            return ResponseEntity.ok(response);
        } else {
            return new ResponseEntity<>("Credenciales inválidas.", HttpStatus.UNAUTHORIZED);
        }
    }
}