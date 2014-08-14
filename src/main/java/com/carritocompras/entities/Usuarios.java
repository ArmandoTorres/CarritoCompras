package com.carritocompras.entities;

import com.carritocompras.util.Estados;
import com.carritocompras.util.Roles;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Clase POJO que representa la tbl de usuarios
 * @author Armando
 * @version 1.0
 */
//<editor-fold defaultstate="collapsed" desc="NamedQueries">
@Entity
@NamedQueries({@NamedQuery(name="FindAllUsuarios",query="SELECT a FROM Usuarios a"),
               @NamedQuery(name="FindAllActiveUsuarios", query="SELECT b FROM Usuarios b WHERE b.status = :p2"),
               @NamedQuery(name="FindUserByEmail",query="SELECT c FROM Usuarios c WHERE c.email = :p1")})
//</editor-fold>
public class Usuarios implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="properties">
    /** Varible utilizada para invocar el NamedQuery correspondiente.*/
    public static final String FIND_ALL_USUARIOS = "FindAllUsuarios";
    /** Varible utilizada para invocar el NamedQuery correspondiente.*/
    public static final String FIND_ALL_ACTIVE_USUARIOS = "FindAllActiveUsuarios";
    /** Varible utilizada para invocar el NamedQuery correspondiente.*/
    public static final String FIND_USER_BY_EMAIL = "FindUserByEmail";
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="attributes">
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_usuario")
    private int id;
    
    private String email;
    
    private String nombre;
    
    private String apellido;
    
    @Column(name="clave")
    private String password;
    
    private transient String password2;
    
    @Column(name="estado")
    @Enumerated(EnumType.STRING)
    private Estados status;
    
    @Enumerated(EnumType.STRING)
    private Roles role;
    
    @Column(name="fecha_creacion")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date initDate;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Methods">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Estados getStatus() {
        return status;
    }

    public void setStatus(Estados status) {
        this.status = status;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
    
    @Override
    public String toString() {
        return nombre+" "+apellido;
    }
    //</editor-fold>
}