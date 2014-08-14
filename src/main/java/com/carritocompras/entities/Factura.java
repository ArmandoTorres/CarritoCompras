package com.carritocompras.entities;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Clase encargada de mapear la tbl de Factura en la base de datos.
 * Esta clase contiene una referencia de usuario para distinguir 
 * que usuario genero dicha factura.
 * @author Armando
 * @version 1.0
 */
//<editor-fold defaultstate="collapsed" desc="NamedQueries">
@Entity
@NamedQueries({@NamedQuery(name="FindAllFactura",query="SELECT a FROM Factura a order by a.fecha"),
               @NamedQuery(name="FindFacturaById",query="SELECT b FROM Factura b WHERE b.id = :p1"),
               @NamedQuery(name="FindAllFacturaByUsuario",query="SELECT c FROM Factura c WHERE c.usuario = :p2 order by c.fecha"),
               @NamedQuery(name="FindFacturaByFecha",query="SELECT d FROM Factura d WHERE d.fecha BETWEEN :p3 and :p4 and d.usuario = :p5")
              })
//</editor-fold>
public class Factura implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="References to NamedQuery">
    /**Identifica el nombre de un query especifico para esta clase.*/
    public static final String FIND_ALL = "FindAllFactura";
    public static final String FIND_BY_ID = "FindFacturaById";
    public static final String FIND_BY_USUARIO = "FindAllFacturaByUsuario";
    public static final String FIND_BY_FECHA = "FindFacturaByFecha";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Properties">
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_factura")
    private int id;
    
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuarios usuario;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="factura")
    private List<FacturaDet> detalleFactura;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="fecha")
    private Date fecha;
    
    @Column(name="total")
    private BigDecimal total;
    
    @Column(name="direccion_envio")
    private String direccion;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gets && Sets">
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public List<FacturaDet> getDetalleFactura() {
        return detalleFactura;
    }

    public void setDetalleFactura(List<FacturaDet> detalleFactura) {
        this.detalleFactura = detalleFactura;
    }    

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }    
    
    //</editor-fold>    

}