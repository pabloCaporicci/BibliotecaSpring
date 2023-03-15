/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.services;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {
    @Autowired
        private EditorialRepositorio editorialRepositorio;
   
    @Transactional 
    public void crearEditorial(String nombre) throws MiException{
        valida(nombre);
        Editorial editorial = new Editorial();        
        editorial.setNombre(nombre);        
        editorialRepositorio.save(editorial);                
    }
    
    public List<Editorial> listarEditoriales(){
        List<Editorial> editoriales = new ArrayList();
        editoriales = editorialRepositorio.findAll();        
        return editoriales;
    }
    
    @Transactional 
        public void modificarEditorial(String id, String nombre) throws MiException{
        valida(nombre);
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);            
        }        
    }
        
     public Editorial getOne(String id){
        return editorialRepositorio.getOne(id);        
    }
    
        
        private void valida (String nombre) throws MiException{
        if (nombre.isEmpty() || nombre == null ){
            throw new MiException("Nombre Editorial Vacío ó Nulo. No Válido");
        }
    }
    
}
