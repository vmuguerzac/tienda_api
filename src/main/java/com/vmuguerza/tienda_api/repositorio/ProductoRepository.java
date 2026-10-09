package com.vmuguerza.tienda_api.repositorio;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vmuguerza.tienda_api.modelo.Producto;

public interface ProductoRepository extends 
                                JpaRepository<Producto, Long>{
    List<Producto> findByNombreProducto(String nombre);
    // Query para buscar productos que tengan un monto mayor al parametro
    List<Producto> findByPrecioGreaterThan(BigDecimal precio);
    List<Producto> findByCategoriaIdOrderByIdAsc(Long categoriaId);

    @Query("""
            SELECT p 
            FROM Producto p
            WHERE p.precio BETWEEN :min AND :max
            """)
    List<Producto> buscarProductoEntreRango(
        @Param("min") BigDecimal min, 
        @Param("max") BigDecimal max
    );
    //Paginacion + busqueda
    Page<Producto> findByNombreProductoContainingIgnoreCase(String nombre, Pageable pageable);
    
}
