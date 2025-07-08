package com.stockgain.api.controller;
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
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PortafolioRepository portafolioRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Map<String, String> payload) {
        String nombre = payload.get("nombre");
        String email = payload.get("email");
        String password = payload.get("password");
        if (usuarioRepository.findByEmail(email).isPresent()) {
            return new ResponseEntity<>("El correo electrónico ya está registrado.", HttpStatus.CONFLICT);
        }
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setContrasenaHash(bCryptPasswordEncoder.encode(password));
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        Portafolio nuevoPortafolio = new Portafolio();
        nuevoPortafolio.setUsuario(usuarioGuardado);
        nuevoPortafolio.setNombrePortafolio("Mi Portafolio Principal");
        nuevoPortafolio.setDescripcion("Portafolio de inversiones principal.");
        nuevoPortafolio.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
        portafolioRepository.save(nuevoPortafolio);
        return new ResponseEntity<>(usuarioGuardado, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            return new ResponseEntity<>("Credenciales inválidas.", HttpStatus.UNAUTHORIZED);
        }
        Usuario usuario = usuarioOpt.get();
        if (bCryptPasswordEncoder.matches(password, usuario.getContrasenaHash())) {
            String token = jwtUtil.generateToken(usuario);
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", usuario);
            return ResponseEntity.ok(response);
        } else {
            return new ResponseEntity<>("Credenciales inválidas.", HttpStatus.UNAUTHORIZED);
        }
    }
}