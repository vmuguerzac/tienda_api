package com.vmuguerza.tienda_api.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.vmuguerza.tienda_api.modelo.Categoria;
import com.vmuguerza.tienda_api.modelo.Producto;
import com.vmuguerza.tienda_api.repositorio.CategoriaEMDAO;
import com.vmuguerza.tienda_api.repositorio.ProductoEMDAO;

@Component 
public class DataLoader implements CommandLineRunner {

    // EntityManager
    private final ProductoEMDAO productoDAO;
    private final CategoriaEMDAO categoriaDAO;

    // Constructor
    public DataLoader(ProductoEMDAO productoDAO, CategoriaEMDAO categoriaDAO){
        this.productoDAO = productoDAO;
        this.categoriaDAO = categoriaDAO;
    }

    @Override
    public void run(String... args) throws Exception {
        probarDAO();
    }

    private void probarDAO(){
        System.out.println("Prueba de Producto EntityManager");
        // Insert
        // Crear categoria
        Categoria cat1 = new Categoria();
        cat1.setNombreCategoria("Audio");
        categoriaDAO.guardarCategoria(cat1);
        System.out.println("Categoria guardada con el ID: " + cat1.getId());
        
        // Crear producto
        Producto prod1 = new Producto();
        prod1.setNombreProducto("Audífonos Inalámbricos");
        prod1.setDescripcion("Estos audífonos son de alta fidelidad con sonido claro.");
        prod1.setPrecio(new BigDecimal(199.90));
        prod1.setSinStock(false);
        prod1.setImagenProducto("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%2Fid%2FOIP.-9Mnt3gM1lzXw-x5JZ6begHaHa%3Fpid%3DApi&f=1&ipt=e119ecafd607b9f8b10e5822d3f608f167568caaf6ad1287428389ade920b9f8");
        prod1.setCategoria(cat1);
        productoDAO.guardarProducto(prod1);
        System.out.println("Producto guardado con el ID: " + prod1.getId());

    }

    
}
