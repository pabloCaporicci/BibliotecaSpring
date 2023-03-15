
package com.egg.biblioteca.services;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
//import java.util.HashSet;
import java.util.List;
import java.util.Optional;
//import java.util.Set;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
        private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional 
    public void crearLibro(Long isbn,String titulo, Integer ejemplares, String idAutor,String idEditorial)  throws MiException {
        
        valida(isbn, titulo, ejemplares, idAutor,idEditorial) ;
        
        
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
     
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setCantidad(ejemplares);   
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        libroRepositorio.save(libro);
    }
    
    public List<Libro> listarLibros(){
        List<Libro> libros = new ArrayList();
        libros = libroRepositorio.findAll();        
        return libros;               
    }
    
    @Transactional 
    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares ) throws MiException{
        valida(isbn, titulo, ejemplares, idAutor,idEditorial) ;
        Optional<Libro> respuesta = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
        
        Autor autor = new Autor();
        Editorial editorial = new Editorial();      
        if(respuestaAutor.isPresent()){
            autor = respuestaAutor.get();            
        }        
        if(respuestaEditorial.isPresent()){
            editorial = respuestaEditorial.get();            
        }        
        if(respuesta.isPresent()){
            Libro libro = respuesta.get();
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setCantidad(ejemplares);
            libroRepositorio.save(libro);            
        }                               
    }
    
   public Libro getOne(Long isbn){
        return libroRepositorio.getOne(isbn);
        //return LibroRepositorio.getOne(isbn);        
    }
    
    private void valida(Long isbn,String titulo, Integer ejemplares, String idAutor,String idEditorial)  throws MiException{
        if (isbn == null){
            throw new MiException("ISBN Nulo. No Válido");
        }
        
        if (titulo == null || titulo.isEmpty()){
            throw new MiException("Título Vacío ó Nulo. No Válido");
        }
        
        if (ejemplares == null){
            throw new MiException("Cantidad de Ejemplares Nulo. No Válido");
        }
        
        if (idAutor == null || idAutor.isEmpty() || idAutor.equalsIgnoreCase("Seleccionar Autor")){
            throw new MiException("Autor Vacío ó Nulo. No Válido");
        }
        
        if (idEditorial == null || idEditorial.isEmpty() || idEditorial.equalsIgnoreCase("Seleccionar Editorial")){
            throw new MiException("Editorial Vacío ó Nulo. No Válido");
        }
    }
}
