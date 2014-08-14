package com.carritocompras.util;

import javax.faces.event.PhaseId;
import javax.faces.event.PhaseEvent;
import javax.servlet.http.HttpSession;
import javax.faces.event.PhaseListener;
import javax.faces.context.FacesContext;
import com.carritocompras.entities.Usuarios;
import javax.faces.application.NavigationHandler;


/**
 * Clase utilizada para como listener para escuchar cada vez que se solicite una 
 * pagina y confirmar que el usuario tiene permisos para acceder a la misma.
 * @author Armando
 * @version 1.0
 */
public class AuthorizationListener implements PhaseListener {
    
    /**
     * Metodo utilizado para evaluar si existe un usuario logiado y si este tiene
     * permisos para acceder a la pagina actualmente servida. La evaluacion va de
     * la siguiente manera. 
     * Carpeta /pages/public: Todo usuario tiene permisos a estas paginas.
     * Carpeta /pages/logged: Solo los usuario que este logiados pueden acceder.
     * Carpeta /pages/admin: Solo los usuario tipo admin pueden acceder.
     * @param event evento disparado por la fase.
     */
    @Override
    public void afterPhase(PhaseEvent event) {
        //Se consigue el contexto actual del parametro event.
        FacesContext context = event.getFacesContext();
        
        //Se consigue el view id que contiene la url de la vista actual
        String currentPage = context.getViewRoot().getViewId();
        
        //Juego de variable que preguntan por un repositorio en especifico
        boolean isAdminRealm = currentPage.contains("/admin/");
        boolean isLoggedRealm = currentPage.contains("/logged/");
        boolean isPublicRealm = currentPage.contains("/public/");
        
        String pagina = "";
        
        HttpSession hs = (HttpSession) context.getExternalContext().getSession(true);
        Usuarios currentUser = (Usuarios) hs.getAttribute("CurrentUser");
        
        //Si el reino no es publico se procede a evaluar los permisos del usuario.
        if (!isPublicRealm) {
            if ((isLoggedRealm && currentUser == null)) {
                pagina = "/pages/public/Login.xhtml";
            } 
            try {
                if (isAdminRealm && currentUser.getRole() != Roles.ADMIN){
                    pagina = "/pages/public/Login.xhtml";
                }
            } catch (NullPointerException ex){
                pagina = "/pages/public/Login.xhtml";
            }
        }
        
        if (pagina.length() > 1){
            //handler que retorna al usuario a la pagina deseada.
            NavigationHandler nav = context.getApplication().getNavigationHandler();
            nav.handleNavigation(context, null, pagina);
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {}
    
    /**
     * Metodo utilizado para decirle al listener que se ejecute segun la fase
     * del ciclo de vida de JSF que se este ejecutando en un momento determinado.
     * @return PhaseId
     */
    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}