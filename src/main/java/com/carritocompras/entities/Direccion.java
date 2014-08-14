package com.carritocompras.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Clase encargada de mapear con la tbl de direccion_envio en la BD.
 * @author Armando
 * @version 1.0
 */
@Entity
@Table(name="direccion_envio")
@NamedQueries({@NamedQuery(name="FindAllDireccion",query="SELECT a FROM Direccion a"),
               @NamedQuery(name="FindAllDireccionByUser",query="SELECT b FROM Direccion b WHERE b.usuario = :p1"),
               @NamedQuery(name="FindPrincipalByUser",query="SELECT c FROM Direccion c WHERE c.principal = :p2 and c.usuario = :p3"),
               @NamedQuery(name="FindDireccionById",query="SELECT d FROM Direccion d WHERE d.id = :p4")
              })
public class Direccion implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Propiedades">
    
    //Propiedades utilizadas para relacionar los nombre de los queries.
    public static final String FIND_ALL = "FindAllDireccion";
    public static final String FIND_ALL_BY_USER = "FindAllDireccionByUser";
    public static final String FIND_PRINCIPAL = "FindPrincipalByUser";
    public static final String FIND_BY_ID = "FindDireccionById";
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_direccion")
    private int id;
    
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuarios usuario;
    
    @ManyToOne
    @JoinColumn(name="id_pais")
    private Pais pais;
    
    @Column(name="nombre_contacto", nullable=false)
    private String nombreContacto;
    
    private String calle1;
    
    private String calle2;
    
    private String ciudad;
    
    @Column(name="zip_code", nullable=false)
    private int zipCode;
    
    private String telefono;
    
    private Boolean principal;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getCalle1() {
        return calle1;
    }

    public void setCalle1(String calle1) {
        this.calle1 = calle1;
    }

    public String getCalle2() {
        return calle2;
    }

    public void setCalle2(String calle2) {
        this.calle2 = calle2;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String cuidad) {
        this.ciudad = cuidad;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Direccion)obj).getId() == this.id;
    }
    //</editor-fold>

}