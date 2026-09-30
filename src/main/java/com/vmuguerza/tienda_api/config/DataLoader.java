package com.vmuguerza.tienda_api.config;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.vmuguerza.tienda_api.modelo.Categoria;
import com.vmuguerza.tienda_api.modelo.Producto;
import com.vmuguerza.tienda_api.repositorio.CategoriaEMDAO;
import com.vmuguerza.tienda_api.repositorio.CategoriaRepository;
import com.vmuguerza.tienda_api.repositorio.ProductoEMDAO;
import com.vmuguerza.tienda_api.repositorio.ProductoRepository;

@Component 
public class DataLoader implements CommandLineRunner {

    // EntityManager
    private final ProductoEMDAO productoDAO;
    private final CategoriaEMDAO categoriaDAO;
    // Repository
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    // Constructor
    public DataLoader(ProductoEMDAO productoDAO, CategoriaEMDAO categoriaDAO, 
        CategoriaRepository categoriaRepository, ProductoRepository productoRepository){
        this.productoDAO = productoDAO;
        this.categoriaDAO = categoriaDAO;
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        probarDAO();
        probarRepository();

    }

    private void probarDAO(){
        System.out.println(" ============== Prueba de Producto EntityManager =================");
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

        // Lectura
        // buscar producto
        Producto prod = productoDAO.buscarPorId(1L);
        System.out.println("Producto: " +
                                        prod.getId() + " - " +
                                        prod.getNombreProducto() + 
                        " Categoria: " + prod.getCategoria().getNombreCategoria());
        // Actualizar producto
        prod.setPrecio(new BigDecimal(240.00));
        prod = productoDAO.guardarProducto(prod);
        System.out.println("Producto: " +
                                        prod.getNombreProducto() + " - " +
                                        prod.getPrecio());
    }
    private void probarRepository(){
        System.out.println("\n============== Prueba de Producto y Categoria Repository =================");
        // Insert
        // Crear categoria
        Categoria cat2 = new Categoria();
        cat2.setNombreCategoria("Laptops");
        categoriaRepository.save(cat2);
        System.out.println("Categoria guardada con el ID: " + cat2.getId());
        
        // Crear producto
        Producto prod2 = new Producto();
        prod2.setNombreProducto("Macbook Pro m2");
        prod2.setDescripcion("Laptop de apple de gran performance.");
        prod2.setPrecio(new BigDecimal(7000.90));
        prod2.setSinStock(false);
        prod2.setImagenProducto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTmsQA4b_YpcKQibhOEcPe8fLXr7u-S0u29CzuLVhGKaXvqh13zTXh6JVuF&s=10");
        prod2.setCategoria(cat2);
        productoRepository.save(prod2);
        System.out.println("Producto guardado con el ID: " + prod2.getId());
        // Categoria obtener todos
        categoriaRepository.findAll().forEach(categoria->
            System.out.println("Categoria: " + categoria.getId() + " - " + categoria.getNombreCategoria())
        ); 
        // Producto obtener todos
        productoRepository.findAll().forEach(producto->
            System.out.println(producto.getId() + " - " + producto.getNombreProducto() + " - " + producto.getPrecio())
        );

        String nombreBuscar = "Macbook Pro m2";
        productoRepository.findByNombreProducto(nombreBuscar).forEach(producto->
            System.out.println(producto == null?"No se encontro el producto":"Producto Encontrado: " + producto.getId())
        );

        productoRepository.findByPrecioGreaterThan(new BigDecimal(1000)).forEach(producto->
            System.out.println(producto == null?"No se encontro el producto":"Producto Encontrado: " + producto.getNombreProducto())
        );

        productoRepository.buscarProductoEntreRango(new BigDecimal(100), new BigDecimal(10000))
                .forEach(producto->
                    System.out.println("Encontrado: " + producto.getNombreProducto())
                );
        

    }
}
