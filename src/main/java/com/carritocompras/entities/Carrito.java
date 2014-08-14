package com.carritocompras.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Clase que representa la tbl de carrito en l bd, esta se encarga de guardar 
 * los items elegidos por el usuario para su proxima compra.
 * @author Armando
 * @version 1.0
 */
@Entity
@NamedQueries({@NamedQuery(name="FindAllCarritoByUser",query="SELECT a FROM Carrito a WHERE a.usuario = :p1"),
               @NamedQuery(name="FindCarritoById",query="SELECT b FROM Carrito b WHERE b.id_carrito = :p2"),
               @NamedQuery(name="FindProductoByUser",query="SELECT c FROM Carrito c WHERE c.producto = :p3 AND c.usuario = :p4")})
public class Carrito implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Propiedades">
    public static final String FIND_ALL = "FindAllCarritoByUser";
    public static final String FIND_BY_ID = "FindCarritoById";
    public static final String VERIFY_DUPLICITY = "FindProductoByUser";
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id_carrito;
    
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuarios usuario;
    
    @ManyToOne
    @JoinColumn(name="id_producto")
    private Productos producto;
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="get's and set's">
    public int getIdCarrito() {
        return id_carrito;
    }

    public void setIdCarrito(int id_carrito) {
        this.id_carrito = id_carrito;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    //</editor-fold>
}