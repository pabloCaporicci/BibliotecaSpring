
package com.egg.biblioteca.services;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {
    
      @Autowired
        private AutorRepositorio autorRepositorio;
    
    @Transactional 
    public void crearAutor(String nombre) throws MiException{        
        valida(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);        
        autorRepositorio.save(autor);              
    }
    
    public List<Autor> listarAutores(){
        List<Autor> autores = new ArrayList();
        autores = autorRepositorio.findAll();        
        return autores;
    }
    
    public Autor getOne(String id){
        return autorRepositorio.getOne(id);
        
    }
    
    
    @Transactional 
    public void modificarAutor(String id, String nombre) throws MiException{
        valida(nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);            
        }        
    }
    private void valida (String nombre) throws MiException{
        if (nombre.isEmpty() || nombre == null ){
            throw new MiException("Nombre Autor Vacío ó Nulo. No Válido");
        }
    }
    
}
