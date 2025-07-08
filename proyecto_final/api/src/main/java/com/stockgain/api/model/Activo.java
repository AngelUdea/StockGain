package com.stockgain.api.model;
import jakarta.persistence.*;
@Entity
@Table(name = "Activos")
public class Activo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_activo;
    @Column(nullable = false, unique = true, length = 15)
    private String ticker;
    @Column(name = "nombre_empresa", nullable = false, length = 255)
    private String nombreEmpresa;
    @Column(name = "pais_origen", length = 5)
    private String paisOrigen;
    @Column(length = 100)
    private String sector;
    @Column(name = "esg_ambiental")
    private Integer esgAmbiental;
    @Column(name = "esg_social")
    private Integer esgSocial;
    @Column(name = "esg_gobernanza")
    private Integer esgGobernanza;
    @Column(name = "esg_calificacion_general", length = 2)
    private String esgCalificacionGeneral;
    public Integer getId_activo() {
        return id_activo;
    }
    public void setId_activo(Integer id_activo) {
        this.id_activo = id_activo;
    }
    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }
    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }
    public String getPaisOrigen() {
        return paisOrigen;
    }
    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }
    public String getSector() {
        return sector;
    }
    public void setSector(String sector) {
        this.sector = sector;
    }
    public Integer getEsgAmbiental() {
        return esgAmbiental;
    }
    public void setEsgAmbiental(Integer esgAmbiental) {
        this.esgAmbiental = esgAmbiental;
    }
    public Integer getEsgSocial() {
        return esgSocial;
    }
    public void setEsgSocial(Integer esgSocial) {
        this.esgSocial = esgSocial;
    }
    public Integer getEsgGobernanza() {
        return esgGobernanza;
    }
    public void setEsgGobernanza(Integer esgGobernanza) {
        this.esgGobernanza = esgGobernanza;
    }
    public String getEsgCalificacionGeneral() {
        return esgCalificacionGeneral;
    }
    public void setEsgCalificacionGeneral(String esgCalificacionGeneral) {
        this.esgCalificacionGeneral = esgCalificacionGeneral;
    }
}