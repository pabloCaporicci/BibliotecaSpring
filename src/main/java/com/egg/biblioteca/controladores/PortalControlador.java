
package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.services.UsuarioServicio;
import javax.servlet.http.HttpSession;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/")
    public String index(){
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar(){
        return "registrar.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password, String password2, ModelMap modelo) {
        
        try {
            usuarioServicio.registrar(nombre, email, password, password2);
            modelo.put("exito","Usuario Registrado Correctamente¡¡¡");
            return "index.html";
        } catch (MiException ex) {
            //Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error",ex.getMessage());
            modelo.put("nombre",nombre);
            modelo.put("email",email);
            
            return "registrar.html";
        }
        
    }

@GetMapping("/login")
    public String login(@RequestParam(required=false) String error, ModelMap modelo){
        if (error != null) {
            modelo.put("error","Usuario o Clave No Valido¡¡¡");            
    }
        return "login.html";
    }

    
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session){
        
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
            
        }
        
        return "inicio.html";
    }
}
    
   

