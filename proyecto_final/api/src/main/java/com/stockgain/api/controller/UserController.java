package com.stockgain.api.controller;

/**
 * USERCONTROLLER.JAVA
 * 
 * Controlador REST para gestión de usuarios en StockGain
 * 
 * Descripción:
 * Este controlador maneja las operaciones relacionadas con la gestión de usuarios
 * autenticados en la aplicación. Proporciona endpoints para actualizar información
 * personal y cambiar contraseñas de forma segura.
 * 
 * Endpoints disponibles:
 * - PUT /api/user/{userId} - Actualizar datos del usuario (nombre, email)
 * - PUT /api/user/{userId}/password - Cambiar contraseña del usuario
 * 
 * Funcionalidades principales:
 * - Actualización de información personal del usuario
 * - Cambio seguro de contraseñas con cifrado BCrypt
 * - Validación de existencia de usuarios
 * - Manejo de errores para usuarios no encontrados
 * - Operaciones protegidas por autenticación JWT
 * 
 * Seguridad:
 * - Endpoints protegidos por autenticación JWT
 * - Contraseñas cifradas con BCrypt antes del almacenamiento
 * - Validación de autorización implícita por ID de usuario
 * - No exposición de contraseñas en responses
 * 
 * Validaciones:
 * - Verificación de existencia del usuario antes de actualizar
 * - Respuestas HTTP apropiadas (404 para usuario no encontrado)
 * - Confirmación de operaciones exitosas
 * 
 * Integración:
 * - Usa UsuarioRepository para persistencia de datos
 * - Utiliza BCryptPasswordEncoder para cifrado de contraseñas
 * - Integrado con sistema de autenticación JWT
 * 
 * @author StockGain Team
 * @version 1.0
 */

import com.stockgain.api.model.Usuario;
import com.stockgain.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    
    // Inyección de dependencias
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    /**
     * Actualiza los datos personales de un usuario
     * 
     * @param userId ID del usuario a actualizar
     * @param payload mapa con los nuevos datos (nombre, email)
     * @return ResponseEntity con confirmación de actualización
     */
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserDetails(@PathVariable Integer userId, @RequestBody Map<String, String> payload) {
        // Buscar usuario por ID
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(userId);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        // Actualizar datos del usuario
        Usuario usuario = usuarioOpt.get();
        usuario.setNombre(payload.get("nombre"));
        usuario.setEmail(payload.get("email"));
        
        // Guardar cambios en la base de datos
        usuarioRepository.save(usuario);
        
        return ResponseEntity.ok("Datos actualizados correctamente.");
    }
    
    /**
     * Cambia la contraseña de un usuario
     * 
     * @param userId ID del usuario
     * @param payload mapa con la nueva contraseña
     * @return ResponseEntity con confirmación de cambio
     */
    @PutMapping("/{userId}/password")
    public ResponseEntity<?> updateUserPassword(@PathVariable Integer userId, @RequestBody Map<String, String> payload) {
        // Buscar usuario por ID
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(userId);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        // Actualizar contraseña con cifrado
        Usuario usuario = usuarioOpt.get();
        String newPassword = payload.get("password");
        usuario.setContrasenaHash(bCryptPasswordEncoder.encode(newPassword));
        
        // Guardar cambios en la base de datos
        usuarioRepository.save(usuario);
        
        return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }
}