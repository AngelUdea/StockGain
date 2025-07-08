package com.stockgain.api.model;

/**
 * ACTIVO.JAVA
 * 
 * Modelo JPA para la entidad Activo en StockGain
 * 
 * Descripción:
 * Esta clase representa los activos financieros disponibles para inversión
 * en la plataforma StockGain. Incluye información básica de la empresa,
 * datos de mercado y calificaciones ESG (Environmental, Social, Governance).
 * 
 * Tabla de base de datos: Activos
 * 
 * Campos principales:
 * - id_activo: Identificador único autoincremental
 * - ticker: Símbolo bursátil único (ej: AAPL, GOOGL)
 * - nombreEmpresa: Nombre completo de la empresa
 * - paisOrigen: Código del país de origen
 * - sector: Sector económico de la empresa
 * - esgAmbiental: Puntuación ESG ambiental (1-100)
 * - esgSocial: Puntuación ESG social (1-100)
 * - esgGobernanza: Puntuación ESG de gobernanza (1-100)
 * - esgCalificacionGeneral: Calificación ESG general (A, B, C)
 * 
 * Características ESG:
 * - Environmental: Impacto ambiental y sostenibilidad
 * - Social: Responsabilidad social corporativa
 * - Governance: Gobernanza y transparencia empresarial
 * - Calificación general: Resumen de las tres dimensiones
 * 
 * Uso en la aplicación:
 * - Referencia para posiciones de inversión
 * - Filtrado por criterios ESG
 * - Análisis de impacto del portafolio
 * - Información para decisiones de inversión
 * 
 * Relaciones:
 * - Un activo puede estar en múltiples posiciones
 * - Relación 1:N con Posicion
 * 
 * @author StockGain Team
 * @version 1.0
 */

import jakarta.persistence.*;

@Entity
@Table(name = "Activos")
public class Activo {
    
    // Identificador único del activo (clave primaria)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_activo;
    
    // Símbolo bursátil único del activo
    @Column(nullable = false, unique = true, length = 15)
    private String ticker;
    
    // Nombre completo de la empresa
    @Column(name = "nombre_empresa", nullable = false, length = 255)
    private String nombreEmpresa;
    
    // País de origen de la empresa
    @Column(name = "pais_origen", length = 5)
    private String paisOrigen;
    
    // Sector económico de la empresa
    @Column(length = 100)
    private String sector;
    
    // Puntuación ESG ambiental (1-100)
    @Column(name = "esg_ambiental")
    private Integer esgAmbiental;
    
    // Puntuación ESG social (1-100)
    @Column(name = "esg_social")
    private Integer esgSocial;
    
    // Puntuación ESG de gobernanza (1-100)
    @Column(name = "esg_gobernanza")
    private Integer esgGobernanza;
    
    // Calificación ESG general (A, B, C)
    @Column(name = "esg_calificacion_general", length = 2)
    private String esgCalificacionGeneral;
    
    // === GETTERS Y SETTERS ===
    
    /**
     * Obtiene el ID único del activo
     * @return Integer ID del activo
     */
    public Integer getId_activo() {
        return id_activo;
    }
    
    /**
     * Establece el ID del activo
     * @param id_activo ID del activo
     */
    public void setId_activo(Integer id_activo) {
        this.id_activo = id_activo;
    }
    
    /**
     * Obtiene el ticker del activo
     * @return String ticker del activo
     */
    public String getTicker() {
        return ticker;
    }
    
    /**
     * Establece el ticker del activo
     * @param ticker símbolo bursátil
     */
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    
    /**
     * Obtiene el nombre de la empresa
     * @return String nombre de la empresa
     */
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }
    
    /**
     * Establece el nombre de la empresa
     * @param nombreEmpresa nombre completo de la empresa
     */
    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }
    
    /**
     * Obtiene el país de origen
     * @return String código del país
     */
    public String getPaisOrigen() {
        return paisOrigen;
    }
    
    /**
     * Establece el país de origen
     * @param paisOrigen código del país
     */
    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }
    
    /**
     * Obtiene el sector de la empresa
     * @return String sector económico
     */
    public String getSector() {
        return sector;
    }
    
    /**
     * Establece el sector de la empresa
     * @param sector sector económico
     */
    public void setSector(String sector) {
        this.sector = sector;
    }
    
    /**
     * Obtiene la puntuación ESG ambiental
     * @return Integer puntuación ambiental (1-100)
     */
    public Integer getEsgAmbiental() {
        return esgAmbiental;
    }
    
    /**
     * Establece la puntuación ESG ambiental
     * @param esgAmbiental puntuación ambiental
     */
    public void setEsgAmbiental(Integer esgAmbiental) {
        this.esgAmbiental = esgAmbiental;
    }
    
    /**
     * Obtiene la puntuación ESG social
     * @return Integer puntuación social (1-100)
     */
    public Integer getEsgSocial() {
        return esgSocial;
    }
    
    /**
     * Establece la puntuación ESG social
     * @param esgSocial puntuación social
     */
    public void setEsgSocial(Integer esgSocial) {
        this.esgSocial = esgSocial;
    }
    
    /**
     * Obtiene la puntuación ESG de gobernanza
     * @return Integer puntuación de gobernanza (1-100)
     */
    public Integer getEsgGobernanza() {
        return esgGobernanza;
    }
    
    /**
     * Establece la puntuación ESG de gobernanza
     * @param esgGobernanza puntuación de gobernanza
     */
    public void setEsgGobernanza(Integer esgGobernanza) {
        this.esgGobernanza = esgGobernanza;
    }
    
    /**
     * Obtiene la calificación ESG general
     * @return String calificación general (A, B, C)
     */
    public String getEsgCalificacionGeneral() {
        return esgCalificacionGeneral;
    }
    
    /**
     * Establece la calificación ESG general
     * @param esgCalificacionGeneral calificación general
     */
    public void setEsgCalificacionGeneral(String esgCalificacionGeneral) {
        this.esgCalificacionGeneral = esgCalificacionGeneral;
    }
}