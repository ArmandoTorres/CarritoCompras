package com.carritocompras.managedbean;

import java.io.Serializable;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import com.carritocompras.util.JSFUtil;
import javax.faces.context.FacesContext;
import com.carritocompras.entities.Usuarios;
import com.carritocompras.dao.impl.CarritoDao;
import com.carritocompras.dao.impl.UsuariosDao;

/**
 * ManagedBean encargado de gestionar el comportamiento de la pagina de Login.xhtml
 * @author Armando
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class LoginMB implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Propiedades">
    /**Objeto que permite interactuar con la tbl de usuarios.*/
    private transient UsuariosDao usuarioDao;
    
    private String userName;
    
    private String userPassword;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's and Set's">
    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @PostConstruct
    public void init(){
        usuarioDao = new UsuariosDao(); //(UsuariosDao) DaoDispacher.getDao(UsuariosDao.class);
    }
    
    /**
     * Este metodo verifica si el usuario y contrasena ingresado son validos,
     * segun lo especificado en la BD. Primero se procede a validar que los 
     * Datos se encuetren llenos, luego se autentica el usuario por usuario 
     * y contrasena si el metodo retorna true entonces se procede a ingresar
     * en la session el objeto CurrentUser que sera el usado para verificar 
     * que el usuario actual esta logiando. La variable url se utiliza para 
     * retornar el index en caso de que sea autentique el usuario correctamente,
     * en caso contrario refresca la pagina.
     * @return url : Retorna la url a la cual se dirigira al usuario.
     */
    public String login() {
        String url = null;
        try {
            if (userName != null && userPassword != null){
                if (usuarioDao.autenticar(userName, userPassword)){
                    HttpSession hs = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                    Usuarios user = usuarioDao.findByName(userName);
                    hs.setAttribute("CurrentUser", user);
                    hs.setAttribute("CurrentUserRole", user.getRole());
                    //Objeto utilizado para saber la cantidad de item dentro del carrito
                    //Se le asigna a la session debido a que el menu se despliega en todas las paginas
                    //y es necesario que se muestre siempre.
                    try {
                        hs.setAttribute("CarritoItems", "("+new CarritoDao().findAllByUser(user).size() +")" );
                    } catch (Exception e) {
                    
                    }
                    url = "/index?faces-redirect=true";
                } else{
                    JSFUtil.showErrorMessage("Usuario y/o Contraseña invalidos.", "El usuario y/o contraseña ingresados no son validos.");
                }    
            } else {
                JSFUtil.showWarmingMessage("Campos Obligatorios", "Debe indicar el nombre y la contraseña.");
            }
        } catch (javax.persistence.NoResultException ex) {
            JSFUtil.showInfoMessage("Usuario no registrado", "EL usuario indicado no se encuentra registrado en la base de datos.");
            return url;
        } 
        return url;
    }
    
    /**
     * Metodo que se encarga de invalidar la session y eliminar el usuario del 
     * contexto actual.
     * @return url del index.
     */
    public String logout(){
        HttpSession hs = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        hs.invalidate();
        return "/index?faces-redirect=true";
    }
    //</editor-fold>
}