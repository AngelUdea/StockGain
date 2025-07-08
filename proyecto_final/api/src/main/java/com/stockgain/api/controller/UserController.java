package com.stockgain.api.controller;
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
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserDetails(@PathVariable Integer userId, @RequestBody Map<String, String> payload) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(userId);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Usuario usuario = usuarioOpt.get();
        usuario.setNombre(payload.get("nombre"));
        usuario.setEmail(payload.get("email"));
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Datos actualizados correctamente.");
    }
    @PutMapping("/{userId}/password")
    public ResponseEntity<?> updateUserPassword(@PathVariable Integer userId, @RequestBody Map<String, String> payload) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(userId);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Usuario usuario = usuarioOpt.get();
        String newPassword = payload.get("password");
        usuario.setContrasenaHash(bCryptPasswordEncoder.encode(newPassword));
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }
}