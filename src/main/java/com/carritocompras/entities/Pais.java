package com.carritocompras.entities;

import java.io.Serializable;
import javax.persistence.*;
import com.carritocompras.util.Estados;

/**
 * Clase que representa la tbl de pais y sus atributos.
 * @author Armando
 * @version 1.0
 */
@Entity
@NamedQueries({
               @NamedQuery(name="FindAllPais",query="SELECT a FROM Pais a WHERE a.estado = :p1"),
               @NamedQuery(name="FindPaisById",query="SELECT b FROM Pais b WHERE b.id = :p2 and b.estado = :p3")
              })
public class Pais implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Propiedades">
    //Propiedad que identifica el nombre del namedquery
    public static final String FIND_ALL = "FindAllPais";
    public static final String FIND_BY_ID = "FindPaisById";
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_pais")
    private int id;
    
    private String nombre;
    
    @Enumerated(EnumType.STRING)
    private Estados estado;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return nombre;
    }
    //</editor-fold>
}