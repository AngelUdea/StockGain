package com.stockgain.api.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
@Entity
@Table(name = "Posiciones")
public class Posicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_posicion;
    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal cantidad;
    @Column(name = "precio_compra_promedio", nullable = false, precision = 18, scale = 4)
    private BigDecimal precioCompraPromedio;
    @Column(name = "fecha_primera_compra")
    @Temporal(TemporalType.DATE)
    private Date fechaPrimeraCompra;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_portafolio", nullable = false)
    private Portafolio portafolio;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_activo", nullable = false)
    private Activo activo;
    public Integer getId_posicion() {
        return id_posicion;
    }
    public void setId_posicion(Integer id_posicion) {
        this.id_posicion = id_posicion;
    }
    public BigDecimal getCantidad() {
        return cantidad;
    }
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }
    public BigDecimal getPrecioCompraPromedio() {
        return precioCompraPromedio;
    }
    public void setPrecioCompraPromedio(BigDecimal precioCompraPromedio) {
        this.precioCompraPromedio = precioCompraPromedio;
    }
    public Date getFechaPrimeraCompra() {
        return fechaPrimeraCompra;
    }
    public void setFechaPrimeraCompra(Date fechaPrimeraCompra) {
        this.fechaPrimeraCompra = fechaPrimeraCompra;
    }
    public Portafolio getPortafolio() {
        return portafolio;
    }
    public void setPortafolio(Portafolio portafolio) {
        this.portafolio = portafolio;
    }
    public Activo getActivo() {
        return activo;
    }
    public void setActivo(Activo activo) {
        this.activo = activo;
    }
}