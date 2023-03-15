package com.egg.biblioteca.repositorios;
import com.egg.biblioteca.entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {
        
  // @Query("SELECT a FROM AUTOR a WHERE a.nombre = :nombre") 
   // public List<Autor> buscarPorNombre(@Param("nombre") String nombre);
    
    @Query("select a from Autor a where a.nombre = :nombre")
    public List<Autor> buscarPorAutor (@Param("nombre")String nombre);
    
    
    
}
