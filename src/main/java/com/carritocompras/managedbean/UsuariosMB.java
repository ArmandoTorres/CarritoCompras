package com.carritocompras.managedbean;

import java.util.List;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import com.carritocompras.util.Estados;
import com.carritocompras.util.JSFUtil;
import com.carritocompras.entities.Usuarios;
import com.carritocompras.dao.impl.UsuariosDao;

/**
 * ManagedBean utilizado para manejar la vista de Usuarios.xhtml
 * @author Armando
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class UsuariosMB implements Serializable {
    
    //Dao de usuarios
    private transient UsuariosDao usuariosDao;
    
    //Listado de usuarios mostrados en pantalla.
    private List<Usuarios> listaUsuarios;
    
    public List<Usuarios> getListaUsuarios() {
        return listaUsuarios;
    }

    /**
     * Metodo que inicializa el objeto Dao de usuarios y la lista de usuarios.
     */
    @PostConstruct
    public void init(){
        usuariosDao = new UsuariosDao();
        listaUsuarios = usuariosDao.findAll();
    }
    
    /**
     * Metodo utilizado para para realizar el cambio de estatus de un usuario.
     * Dependiendo del estatus que tenga el usuario.
     * @return String null para refrescar la pagina.
     * @param user usuario a modificar.
     */
    public String cambiarStatus(Usuarios user){
        if (user.getStatus() == Estados.INACTIVO){
            user.setStatus(Estados.ACTIVO);
        } else {
            user.setStatus(Estados.INACTIVO);
        }
        try {
            usuariosDao.update(user);
        } catch (Exception ex) {
            Logger.getLogger(UsuariosMB.class.getName()).log(Level.SEVERE, null, ex);
            JSFUtil.showInfoMessage("Error", "Ocurrio un error al momento de cambiar el estatus al usuario.");
        }
        return null;
    }
    
}