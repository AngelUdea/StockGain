package com.stockgain.api.model;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
@Entity
@Table(name = "Portafolios")
public class Portafolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_portafolio;
    @Column(name = "nombre_portafolio", nullable = false, length = 100)
    private String nombrePortafolio;
    @Column(length = 255)
    private String descripcion;
    @Column(name = "fecha_creacion", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaCreacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    @OneToMany(mappedBy = "portafolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Posicion> posiciones;
    public Integer getId_portafolio() {
        return id_portafolio;
    }
    public void setId_portafolio(Integer id_portafolio) {
        this.id_portafolio = id_portafolio;
    }
    public String getNombrePortafolio() {
        return nombrePortafolio;
    }
    public void setNombrePortafolio(String nombrePortafolio) {
        this.nombrePortafolio = nombrePortafolio;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Set<Posicion> getPosiciones() {
        return posiciones;
    }
    public void setPosiciones(Set<Posicion> posiciones) {
        this.posiciones = posiciones;
    }
}