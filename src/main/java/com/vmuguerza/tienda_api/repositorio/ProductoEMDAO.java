package com.vmuguerza.tienda_api.repositorio;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.vmuguerza.tienda_api.modelo.Producto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository 
public class ProductoEMDAO {
    
    @PersistenceContext 
    private EntityManager entityManager;

    public Producto buscarPorId(Long id){
        return entityManager.find(Producto.class, id);
    }

    public List<Producto> buscarProductos(){
        String query = """
            SELECT p FROM Producto p
        """;
        return entityManager.createQuery(query, Producto.class).getResultList();
    }
    @Transactional 
    public Producto guardarProducto(Producto producto){
        if(producto.getId() == null){
            // Guardar
            entityManager.persist(producto); // INSERT
            return producto;
        }else{
            return entityManager.merge(producto); // UPDATE
        }
    }
    @Transactional
    public void eliminarProducto(Long id){
        Producto producto = buscarPorId(id); // BUSCAR
        if(producto != null){
            entityManager.remove(producto); // ELIMINAR
        }
    }

    // QUERY CON SQL
    public List<Producto> buscarPorNombreJPQL(String nombre){
        String query = """
                SELECT p 
                FROM Producto p
                WHERE p.nombre LIKE :nombre
        """;
        return entityManager.createQuery(query).setParameter("nombre", "%" + nombre + "%").getResultList();

    }

    
}
