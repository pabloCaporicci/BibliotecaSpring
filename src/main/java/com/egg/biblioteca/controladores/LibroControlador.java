

package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.services.AutorServicio;
import com.egg.biblioteca.services.EditorialServicio;
import com.egg.biblioteca.services.LibroServicio;
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
@RequestMapping("/libro")
public class LibroControlador {
    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;
        
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo){
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("autores",autores);
        modelo.addAttribute("editoriales",editoriales);
        return "libro_form.html";
    }
    
     /* desde el action del form del html lo llama.. el atributo name del input tiene que ser con el mismo nombre */
    /*public void crearLibro(Long isbn,String titulo, Integer ejemplares, String idAutor,String idEditorial)  throws MiException {*/
    @PostMapping("/registro")
    public String registro(@RequestParam(required=false)   Long isbn,@RequestParam String titulo,@RequestParam(required=false) Integer ejemplares,
                                      @RequestParam String idAutor,@RequestParam String idEditorial, ModelMap modelo){
        try {
            libroServicio.crearLibro(isbn,titulo,ejemplares,idAutor,idEditorial);
            modelo.put("exito","Libro Registrado Correctamente¡¡¡");
        } catch (MiException ex) {
            //Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();
            modelo.addAttribute("autores",autores);
            modelo.addAttribute("editoriales",editoriales);

            modelo.put("error",ex.getMessage());
            return "libro_form.html";
        }
        return "index.html";        
    }
    
    @GetMapping("/listar")
    public String listar(ModelMap modelo){
        List<Libro>libros = libroServicio.listarLibros();
        modelo.addAttribute("libros",libros);                       
        return "libro_list.html";
    }
    
    
     @GetMapping("/modificar/{isbn}")
     public String modificar(@PathVariable Long isbn, ModelMap modelo){
            
            Libro librito = libroServicio.getOne(isbn);
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();
           autores.remove(librito.getAutor());
           editoriales.remove(librito.getEditorial());
            
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            
            
            
            modelo.put("libro",librito);            
            
            
            
            return "libro_modificar.html";
    }
     
    @PostMapping("/modificar/{isbn}")
    // public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares )
    public String modificar(@PathVariable Long isbn, String titulo,String idAutor,String idEditorial,Integer cantidad, ModelMap modelo, RedirectAttributes redireccion) {        
        try {
            libroServicio.modificarLibro(isbn, titulo, idAutor,idEditorial, cantidad);
            return "redirect:../listar";
        } catch (MiException ex) {
            
            redireccion.addFlashAttribute("error", ex.getMessage());
//            modelo.put("error", ex.getMessage());                                    
            //return "autor_modificar.html";            
            return "redirect:/libro/modificar/"+isbn;
        }
        
    }

    
}
