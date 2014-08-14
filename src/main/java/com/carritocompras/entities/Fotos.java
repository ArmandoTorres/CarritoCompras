package com.carritocompras.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Clase que representa la tbl de fotos_productos en la BD, esta contiene todas
 * las fotos que se encuentran enlazadas a un producto en especifico.
 * @author Armando
 * @version 1.0
 */
@Entity
@Table(name="fotos_productos")
public class Fotos implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id_foto;
    
    @ManyToOne
    @JoinColumn(name="id_producto")
    private Productos producto;
    
    //nombre de la foto con su extension
    private String foto;
    
    //Ruta de la foto
    private String ruta;
    
    public int getId_foto() {
        return id_foto;
    }

    public Fotos() {
    }
    
    public Fotos(Productos producto, String foto, String ruta) {
        this.producto = producto;
        this.foto = foto;
        this.ruta = ruta;
    }

    public void setId_foto(int id_foto) {
        this.id_foto = id_foto;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public String toString() {
        return foto;
    }
}