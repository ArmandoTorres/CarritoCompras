package com.carritocompras.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * Clase POJO encargada de mapear con la tbl Detalle_factura en la base de datos.
 * @author Armando
 * @version 1.0
 */
@Entity
@Table(name="detalle_factura")
public class FacturaDet implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Properties">
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_detalle_factura")
    private int id;
    
    @ManyToOne
    @JoinColumn(name="id_factura")
    private Factura factura;
    
    @ManyToOne
    @JoinColumn(name="id_producto")
    private Productos producto;
    
    @Column(name="cantidad")
    private int cantidad;
    
    @Column(name="precio")
    private BigDecimal precio;
    
    @Column(name="total")
    private BigDecimal total;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Methods">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    //</editor-fold>
}