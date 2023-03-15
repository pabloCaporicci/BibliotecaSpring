
package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.services.EditorialServicio;
import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    private EditorialServicio editorialServicio;
    
     @GetMapping("/registrar")
    public String registrar(){
        return "editorial_form.html";
    }
    
    
     /* desde el action del form del html lo llama.. el atributo name del input tiene que ser con el mismo nombre */
    @PostMapping("/registro")
    public String registro(@RequestParam   String nombre, ModelMap modelo){
        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito","Editorial Registrada Correctamente¡¡¡");
        } catch (MiException ex) {
            //Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error",ex.getMessage());
            return "autor_form.html";
        }
        return "index.html";        
    }
    
   @GetMapping("/listar")
    public String listar(ModelMap modelo){
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("editoriales",editoriales);                       
        return "editorial_list.html";
    }   

    @GetMapping("/modificar/{id}")
     public String modificar(@PathVariable String id, ModelMap modelo){
            modelo.put("editorial",editorialServicio.getOne(id));
            return "editorial_modificar.html";
    }
     
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo, RedirectAttributes redireccion) {        
        try {
            editorialServicio.modificarEditorial(id, nombre);
            return "redirect:../listar";
        } catch (MiException ex) {
            
            redireccion.addFlashAttribute("error", ex.getMessage());
//            modelo.put("error", ex.getMessage());                                    
            //return "autor_modificar.html";            
            return "redirect:/editorial/modificar/"+id;
        }
        
    }


}
