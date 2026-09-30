package com.vmuguerza.tienda_api.servicios;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vmuguerza.tienda_api.modelo.Producto;
import com.vmuguerza.tienda_api.repositorio.ProductoRepository;

@Service 
public class ProductoService {

    private final ProductoRepository productoRepository;
    // Constructor
    public ProductoService(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    public List<Producto> mostrarTodosLosProductos(){
        return productoRepository.findAll();
    }

    public Optional<Producto> buscarProductoPorId(Long id){
        return productoRepository.findById(id);
    }

    public Producto registrarProducto(String nombreProducto, String descripcion, 
        BigDecimal precio, String imagenProducto){
            // Validaciones
            if(nombreProducto == null || nombreProducto.isBlank()){
                throw new IllegalArgumentException("El nombre del producto es obligatorio");
            }
            if(precio == null || precio.compareTo(BigDecimal.ZERO) <= 0){
                throw new IllegalArgumentException("El precio debe ser mayor a 0");
            }
            // validar mas
            Producto producto = new Producto();
            producto.setNombreProducto(nombreProducto);
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setImagenProducto(imagenProducto);
            return productoRepository.save(producto);
        }


    
}
