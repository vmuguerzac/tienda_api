package com.vmuguerza.tienda_api.repositorio;

import org.springframework.stereotype.Repository;

import com.vmuguerza.tienda_api.modelo.Categoria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository 
public class CategoriaEMDAO {
    @PersistenceContext 
    private EntityManager entityManager;

    public Categoria guardarCategoria(Categoria categoria){
        if(categoria.getId() == null){
            entityManager.persist(categoria);
            return categoria;
        }else{
            return entityManager.merge(categoria);
        }
    }
    
}
