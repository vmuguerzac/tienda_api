package com.vmuguerza.tienda_api.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vmuguerza.tienda_api.modelo.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
