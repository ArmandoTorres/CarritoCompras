package com.carritocompras.managedbean;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import com.carritocompras.util.Roles;
import javax.annotation.PostConstruct;
import com.carritocompras.util.JSFUtil;
import com.carritocompras.util.Estados;
import com.carritocompras.entities.Pais;
import com.carritocompras.dao.impl.PaisDao;
import javax.persistence.NoResultException;
import com.carritocompras.entities.Usuarios;
import com.carritocompras.entities.Direccion;
import com.carritocompras.dao.impl.UsuariosDao;
import com.carritocompras.dao.impl.DireccionDao;


/**
 * Managed bean utilizado para manejar las acciones sobre las vistas de:
 * /pages/admin/AgregarUsuarios.xhtml
 * /pages/public/CrearCuenta.xhtml
 * /pages/logged/AgregarDireccionEnvio.xhtml
 * @author Armando
 */
@ManagedBean
@ViewScoped
public class AgregarUsuariosMB implements Serializable {
    
    //Implementacion del Dao de usuarios.
    private transient UsuariosDao usuariosDao;
    
    //Implementacion del Dao de Direccion.
    private transient DireccionDao direccionDao;
    
    //Implementacion del Dao de Pais.
    private transient PaisDao paisDao;
    
    //Objeto Usuarios que sera almacenado en la BD.
    private Usuarios usuario;
    
    //Objeto Direccion que sera almacenado en la BD.
    private Direccion direccion;
    
    //Id del pais seleccionado por el usuario en pantalla.
    private int pais;
    
    //Listado de paises a mostrar en pantalla.
    private List<Pais> listaPaises;

    //<editor-fold defaultstate="collapsed" desc="Gets and Sets">
    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public int getPais() {
        return pais;
    }

    public void setPais(int pais) {
        this.pais = pais;
    }

    public List<Pais> getListaPaises() {
        return listaPaises;
    }
    //</editor-fold>
    
    /**
     * Metodo encargado de inicilizar los objetos requeridos.
     * Tambien se encarga de llenar la lista de paises.
     */
    @PostConstruct
    public void init(){
        usuariosDao  = new UsuariosDao();
        direccionDao = new DireccionDao();
        paisDao = new PaisDao();
        usuario   = new Usuarios();
        direccion = new Direccion();
        listaPaises = paisDao.findAll();
    }
    
    /**
     * Metodo usado para suministrar los valores a la etiquera <f:selectItems/>
     * en pantalla en este caso se llena con la lista de roles que tiene la aplicacion.
     * @return Lista de Roles aceptados por la aplicacion.
     */
    public List<SelectItem> getRoleItems(){
        List<SelectItem> lista = new ArrayList<SelectItem>();
        for (Roles r : Roles.values()){
            lista.add(new SelectItem(r.toString(),r.toString()));
        }
        return lista;
    }
    
    /**
     * Metodo encargado de crear un usuario ese se encuentra referenciado en la 
     * vista de /pages/admin/AgregarUsuarios.xhtml.
     * @return String con la nueva ruta de la pagina.
     */
    public String guardar(){
        try {
            if (usuario.getPassword().equals(usuario.getPassword2())){
                try {
                    usuariosDao.findByName(usuario.getEmail());
                    JSFUtil.showErrorMessage("Email Existente", "El correo ya se encuentra registrado.");
                } catch (NoResultException ex){
                    usuario.setStatus(Estados.ACTIVO);
                    usuario.setInitDate(new Date());
                    usuariosDao.insert(usuario);
                    usuario = new Usuarios();
                    JSFUtil.showInfoMessage("Guardado", "Usuario creado con exito");
                }
            } else {
                JSFUtil.showWarmingMessage("Claves incorrectas", "Ambas claves deben de ser identicas.");
            }
        } catch (Exception e) {
            JSFUtil.showErrorMessage("Error", "Ha ocurrido un error al momento de guardar. " + e);
        }
        return null;
    }
    
    /**
     * Metodo utilizado en la pagina de /pages/public/CrearCuenta.xhtml para
     * crear una cuenta de usuario junto a su direccion de envio principal.
     * @return Url a la cual sera redirigida la pagina.
     */
    public String guardarCuenta(){
        try {
            if (usuario.getPassword().equals(usuario.getPassword2())){
                try {
                    usuariosDao.findByName(usuario.getEmail());
                    JSFUtil.showErrorMessage("Email Existente", "El correo ya se encuentra registrado.");
                } catch (NoResultException ex){
                    usuario.setStatus(Estados.ACTIVO);
                    usuario.setInitDate(new Date());
                    usuario.setRole(Roles.PARTICIPANTE);
                    usuario = usuariosDao.insert(usuario);
                    direccion.setPais(paisDao.findById(pais));
                    direccion.setUsuario(usuario);
                    direccion.setPrincipal(Boolean.TRUE);
                    direccionDao.insert(direccion);
                    JSFUtil.showInfoMessage("Guardado", "Usuario creado con exito");
                    return "/pages/public/Login?faces-redirect=true";
                }
            } else {
                JSFUtil.showWarmingMessage("Claves incorrectas", "Ambas claves deben de ser identicas.");
            }
        } catch (Exception e) {
            JSFUtil.showErrorMessage("Error", "Ha ocurrido un error al momento de guardar. " + e);
        }
        return null;
    }
    
    /**
     * Metodo usado para agregar una direccion de envio al usuario actual de la aplicacion.
     * Este es usado en la vista /pages/logged/AgregarDireccionEnvio.xhtml
     * @return Url a la cual sera redirigida la pagina.
     */
    public String agregarDireccion(){
        try {
            direccion.setPais(paisDao.findById(pais));
            direccion.setPrincipal(Boolean.FALSE);
            direccion.setUsuario(JSFUtil.getCurrentUser());
            direccionDao.insert(direccion);
        } catch (Exception ex) {
            Logger.getLogger(AgregarUsuariosMB.class.getName()).log(Level.SEVERE, null, ex);
            JSFUtil.showErrorMessage("Error", "Ha ocurrido un error al guardar la nueva direccion.");
            return null;
        }
        return "/pages/logged/DireccionesEnvio?faces-redirect=true";
    }
}