package com.stockgain.api.model;

/**
 * USUARIO.JAVA
 * 
 * Modelo JPA para la entidad Usuario en StockGain
 * 
 * Descripción:
 * Esta clase representa a los usuarios registrados en la plataforma StockGain.
 * Almacena información básica de autenticación y perfil de cada usuario,
 * incluyendo credenciales cifradas y metadatos de registro.
 * 
 * Tabla de base de datos: Usuarios
 * 
 * Campos principales:
 * - id_usuario: Identificador único autoincremental
 * - nombre: Nombre completo del usuario
 * - email: Dirección de correo electrónico única
 * - contrasenaHash: Contraseña cifrada con BCrypt
 * - fechaRegistro: Timestamp automático de creación
 * 
 * Características:
 * - Entidad JPA con mapeo automático a tabla
 * - Email único para prevenir duplicados
 * - Contraseña almacenada como hash seguro
 * - Fecha de registro automática
 * - Validaciones de nulidad en campos críticos
 * 
 * Relaciones:
 * - Un usuario puede tener múltiples portafolios
 * - Relación 1:N con Portafolio
 * 
 * Seguridad:
 * - Contraseñas nunca almacenadas en texto plano
 * - Email único para identificación
 * - Validación de campos obligatorios
 * 
 * @author StockGain Team
 * @version 1.0
 */

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Usuarios")
public class Usuario {
    
    // Identificador único del usuario (clave primaria)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;
    
    // Nombre completo del usuario
    @Column(nullable = false, length = 100)
    private String nombre;
    
    // Dirección de correo electrónico (debe ser única)
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    // Hash de la contraseña (cifrada con BCrypt)
    @Column(name = "contrasena_hash", nullable = false, length = 255)
    private String contrasenaHash;
    
    // Fecha y hora de registro del usuario (automático)
    @Column(name = "fecha_registro", updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaRegistro;
    
    // === GETTERS Y SETTERS ===
    
    /**
     * Obtiene el ID único del usuario
     * @return Integer ID del usuario
     */
    public Integer getId_usuario() {
        return id_usuario;
    }
    
    /**
     * Establece el ID del usuario
     * @param id_usuario ID del usuario
     */
    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }
    
    /**
     * Obtiene el nombre completo del usuario
     * @return String nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Establece el nombre del usuario
     * @param nombre nombre completo del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Obtiene el email del usuario
     * @return String email del usuario
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Establece el email del usuario
     * @param email dirección de correo electrónico
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Obtiene el hash de la contraseña
     * @return String hash de la contraseña
     */
    public String getContrasenaHash() {
        return contrasenaHash;
    }
    
    /**
     * Establece el hash de la contraseña
     * @param contrasenaHash hash cifrado de la contraseña
     */
    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }
    
    /**
     * Obtiene la fecha de registro del usuario
     * @return Timestamp fecha de registro
     */
    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }
    
    /**
     * Establece la fecha de registro del usuario
     * @param fechaRegistro timestamp de registro
     */
    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}