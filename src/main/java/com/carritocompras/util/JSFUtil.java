package com.carritocompras.util;

import java.io.File;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import java.util.Calendar;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpSession;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;
import org.apache.commons.io.FilenameUtils;
import javax.faces.application.FacesMessage;
import com.carritocompras.entities.Usuarios;


/**
 * Clase de utilidad usada para realizar los metodos para 
 * las acciones mas cotidianas realizadas en la aplicacion.
 * @author Armando
 */
public class JSFUtil {
    
    /**
     * Metodo usado para mostrar un Mensaje de tipo Informacion.
     * @param title Titulo del mensaje.
     * @param details Detalle del mensaje.
     */
    public static void showInfoMessage(String title, String details){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,title, details);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    /**
     * Metodo usado para mostrar un Mensaje de tipo Error.
     * @param title Titulo del mensaje.
     * @param details Detalle del mensaje.
     */
    public static void showErrorMessage(String title, String details){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,title, details);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Metodo usado para mostrar un Mensaje de tipo Fatal.
     * @param title Titulo del mensaje.
     * @param details Detalle del mensaje.
     */    
    public static void showFatalMessage(String title, String details){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL,title, details);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    /**
     * Metodo usado para mostrar un Mensaje de tipo Warming.
     * @param title Titulo del mensaje.
     * @param details Detalle del mensaje.
     */    
    public static void showWarmingMessage(String title, String details){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,title, details);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    /**
     * Metodo usado para conseguir el usuario actual de la aplicacion.
     * @return Usuarios actualmente logiado.
     */
    public static Usuarios getCurrentUser() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) ctx.getExternalContext().getSession(false);
        if (hs.getAttribute("CurrentUser") != null) {
            Usuarios user = (Usuarios) hs.getAttribute("CurrentUser");
            return user;
        }
        return null;
    }
    
    /**
     * Metodo encargado de agregar un item al SessionScope.
     * @param name nombre del objeto a agregar.
     * @param obj Objeto a agregar.
     */
    public static void addItemToSession(String name, Object obj){
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) ctx.getExternalContext().getSession(false);
        hs.setAttribute(name, obj);
    }
    
    /**
     * Metodo usado para conseguir un parametro del RequestMap actual.
     * @return Object contenido en el mapa de request.
     * @param name Nombre del objeto a buscar.
     */
    public static Object getParameterFromRequestMap(String name){
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map<String, String> map = ctx.getExternalContext().getRequestParameterMap();
        return map.get(name);
    }
    
    /**
     * Metodo usado para guardar un archivo en la aplicacion.
     * Este metodo sube el archivo a una ruta generica propia de la aplicacion
     * pero cuyo scope esta fuera del contexto de la misma. Esta ruta se puede
     * cambiar en el archivo Web.xml
     * @return Map<String,String> que contiene el nombre y la ruta donde se guardo el archivo.
     * @param file Archivo a cargar en la aplicacion.
     * @exception IOException prosible exception lanzada por las utilidades de IO.
     */
    public static Map<String,String> saveFile(UploadedFile file) throws IOException {
 
        //Context param con la ruta de las imagenes.
        String ruta = getInitParam("IMAGE_UPLOAD_PATH");
    
        //Carpeta donde se almacena la imagen se genera por ano y mes
        //Se forma la ruta del archivo con el ano actual y el mes actual
        //ojo se le suma uno al mes debido a que los meses de Calendar.MONTH 
        //Empiezan en 0 y no en 1.
        String folderName = Calendar.getInstance().get(Calendar.YEAR)
                                +"\\"+
                               String.format("%02d",(Calendar.getInstance().get(Calendar.MONTH)+1));
        
        //Ruta completa del folder.
        File folder = new File(ruta+folderName); 
        
        //De no existir el folder se crea.
        if (!folder.exists()){
            folder.mkdirs();
        }
        
        InputStream inStr = file.getInputstream();

        //formato de nombre archivo.
        String extension = FilenameUtils.getExtension(file.getFileName());
        String newFileName = new SimpleDateFormat("dd-MM-yyyy-HHmmss").format(new Date())+"."+extension;
        OutputStream out  = new FileOutputStream(new File(folder,newFileName)); 
        
        int read = 0;
        byte[] bytes = new byte[1024];
        
        while ((read = inStr.read(bytes)) != -1){
            out.write(bytes,0,read);
        }
        
        inStr.close();
        out.flush();
        out.close();
        
        Map<String, String> map = new HashMap<String,String>();
        map.put("ruta", folderName);
        map.put("archivo", newFileName);
        
        return map;
    }
    
    /**
     * Metodo creado para conseguir un InitParam del contexto actual.
     * @param name nombre del parametro.
     * @return Valor del parametro.
     */
    public static String getInitParam(String name){
        String param = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(name);
        return param;
    }
    
}