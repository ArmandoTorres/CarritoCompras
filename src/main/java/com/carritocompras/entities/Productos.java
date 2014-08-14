package com.carritocompras.entities;

import java.util.List;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Clase encargada de mapear la tbl de productos en la base de datos.
 * @author Armando
 * @version 1.0
 */
//<editor-fold defaultstate="collapsed" desc="Queries">
@Entity
@NamedQueries({@NamedQuery(name="FindAllProductos",query="SELECT a FROM Productos a"),
               @NamedQuery(name="FindAllProductosWithStock",query="SELECT b FROM Productos b where b.cantidad > 0"),
               @NamedQuery(name="FindProductosById",query="SELECT c FROM Productos c WHERE c.id = :p1"),
               @NamedQuery(name="FindProductoByParameter", query="SELECT d FROM Productos d WHERE (d.titulo like :p2 OR d.descripcion like :p2) "
                       + "and d.cantidad >0")
              })
//</editor-fold>
public class Productos implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Properties">
    /**Variable que identifica un query propio de la clase.*/
    public static final String FIND_ALL_PRODUCTOS = "FindAllProductos";
    /**Variable que identifica un query propio de la clase.*/
    public static final String FIND_ALL_PRODUCTOS_WITH_STOCK = "FindAllProductosWithStock";
    /**Variable que identifica un query propio de la clase.*/
    public static final String FIND_PRODUCTOS_BY_ID = "FindProductosById";
    
    public static final String FIND_BY_PARAMETER = "FindProductoByParameter";
    
    /**Identificador unico de la clase.*/
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_producto")
    private int id;
    
    /**Descripcion del producto.*/
    private String descripcion;
    /**Precio del producto*/
    private BigDecimal precio;
    /**Cantidad de producto existente.*/
    private int cantidad;
    
    private String titulo;
    
    @OneToMany(mappedBy="producto", cascade=CascadeType.ALL)
    private List<Fotos> fotos;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gets and Sets">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public List<Fotos> getFotos() {
        return fotos;
    }

    public void setFotos(List<Fotos> fotos) {
        this.fotos = fotos;
    }
    //</editor-fold>
}