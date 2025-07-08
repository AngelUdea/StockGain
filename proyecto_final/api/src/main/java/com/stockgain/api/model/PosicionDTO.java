package com.stockgain.api.model;
import java.math.BigDecimal;
public class PosicionDTO {
    private Integer id_posicion;
    private String ticker;
    private String nombreEmpresa;
    private BigDecimal cantidad;
    private BigDecimal precioCompraPromedio;
    private String esgCalificacionGeneral;
    public PosicionDTO(Integer id_posicion, String ticker, String nombreEmpresa, BigDecimal cantidad, BigDecimal precioCompraPromedio,
            String esgCalificacionGeneral) {
        this.id_posicion = id_posicion;
        this.ticker = ticker;
        this.nombreEmpresa = nombreEmpresa;
        this.cantidad = cantidad;
        this.precioCompraPromedio = precioCompraPromedio;
        this.esgCalificacionGeneral = esgCalificacionGeneral;
    }
    public static PosicionDTO fromEntity(Posicion posicion) {
        return new PosicionDTO(
            posicion.getId_posicion(),
            posicion.getActivo().getTicker(),
            posicion.getActivo().getNombreEmpresa(),
            posicion.getCantidad(),
            posicion.getPrecioCompraPromedio(),
            posicion.getActivo().getEsgCalificacionGeneral()
        );
    }
    public Integer getId_posicion() { return id_posicion; }
    public void setId_posicion(Integer id_posicion) { this.id_posicion = id_posicion; }
    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }
    public String getNombreEmpresa() { return nombreEmpresa; }
    public void setNombreEmpresa(String nombreEmpresa) { this.nombreEmpresa = nombreEmpresa; }
    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }
    public BigDecimal getPrecioCompraPromedio() { return precioCompraPromedio; }
    public void setPrecioCompraPromedio(BigDecimal precioCompraPromedio) { this.precioCompraPromedio = precioCompraPromedio; }
    public String getEsgCalificacionGeneral() { return esgCalificacionGeneral; }
    public void setEsgCalificacionGeneral(String esgCalificacionGeneral) { this.esgCalificacionGeneral = esgCalificacionGeneral; }
}