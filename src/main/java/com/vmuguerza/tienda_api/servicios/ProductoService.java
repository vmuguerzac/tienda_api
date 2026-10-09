package com.vmuguerza.tienda_api.servicios;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vmuguerza.tienda_api.dto.PaginaResponse;
import com.vmuguerza.tienda_api.dto.ProductoRequest;
import com.vmuguerza.tienda_api.dto.ProductoResponse;
import com.vmuguerza.tienda_api.modelo.Categoria;
import com.vmuguerza.tienda_api.modelo.Producto;
import com.vmuguerza.tienda_api.repositorio.CategoriaRepository;
import com.vmuguerza.tienda_api.repositorio.ProductoRepository;

@Service 
public class ProductoService {
    private static final BigDecimal PRECIO_MINIMO = new BigDecimal("1.00");
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    // Constructor
    public ProductoService(ProductoRepository productoRepository,
        CategoriaRepository categoriaRepository
    ){
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    
    @Transactional(readOnly = true)
    public Optional<ProductoResponse> buscarProductoPorId(Long id){
        Optional<Producto> resultado = productoRepository.findById(id);
        if(resultado.isPresent()){
            return resultado.map(this::aResponse);
        }
        return Optional.empty();
    }

    @Transactional 
    public ProductoResponse crearProducto(ProductoRequest request){
        validarRequest(request);
        // validar mas
        Producto producto = new Producto();
        copiarDatos(producto, request);
        return aResponse(productoRepository.save(producto));
    }

    @Transactional 
    public Optional<ProductoResponse> actualizarProducto(Long id, ProductoRequest request){
        validarRequest(request);
        return productoRepository.findById(id).map(producto-> {
            copiarDatos(producto, request);
            return aResponse(productoRepository.save(producto));
        });
    }

    @Transactional 
    public boolean eliminarProducto(Long id){
        if(!productoRepository.existsById(id)){
            return false;
        }
        productoRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly=true)
    public PaginaResponse<ProductoResponse> listarPaginando(String nombre, Pageable pageable){
        Page<Producto> pagina;
        if(nombre == null || nombre.isBlank()){
            pagina = productoRepository.findAll(pageable);
        }else{
            pagina = productoRepository.findByNombreProductoContainingIgnoreCase(nombre, pageable);
        }
        return PaginaResponse.de(pagina.map(this::aResponse));
    }


    @Transactional
    public void aplicarDescuentoACategoria(Long idCategoria, int porcentaje){
        //fail check
        if(porcentaje < 1 || porcentaje > 99){
            throw new IllegalArgumentException("El porcentaje debe estar entre 1 y 99");
        }
        List<Producto> productos = productoRepository.findByCategoriaIdOrderByIdAsc(idCategoria);
        BigDecimal factor = BigDecimal.valueOf(100 - porcentaje).divide(BigDecimal.valueOf(100));

        for(Producto producto : productos){
            BigDecimal nuevoPrecio = producto.getPrecio().multiply(factor).setScale(2, RoundingMode.HALF_UP);
            if(nuevoPrecio.compareTo(PRECIO_MINIMO) < 0){
                throw new IllegalArgumentException(
                    "El precio de " + producto.getNombreProducto() + " quedaria por debajo de (S/ 1.00)"
                );
            }
            producto.setPrecio(nuevoPrecio);
            productoRepository.save(producto);

        }

    }

    private void validarRequest(ProductoRequest request){
        // Validaciones
        if(request.nombreProducto() == null || request.nombreProducto().isBlank()){
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if(request.precio() == null || request.precio().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        if(request.imagenProducto() != null && !request.imagenProducto().isBlank() && 
            !(request.imagenProducto().startsWith("http://") || 
                request.imagenProducto().startsWith("https://"))){
            throw new IllegalArgumentException("La url debe estar bien formada");
        }
    }

    private ProductoResponse aResponse(Producto p){
        Categoria categoria = p.getCategoria();
        return new ProductoResponse(
            p.getId(),
            p.getNombreProducto(),
            p.getDescripcion(),
            p.getPrecio(),
            p.isSinStock(),
            p.getImagenProducto(),
            categoria != null ? categoria.getId():null
        );
    }

    private void copiarDatos(Producto producto, ProductoRequest request){
        producto.setNombreProducto(request.nombreProducto());
        producto.setDescripcion(request.descripcion());
        producto.setSinStock(request.sinStock());
        producto.setPrecio(request.precio());
        producto.setSinStock(Boolean.TRUE.equals(request.sinStock()));
        producto.setImagenProducto(request.imagenProducto());
        Categoria categoria = null;
        if(request.categoriaId() != null){
            categoria = categoriaRepository.findById(request.categoriaId()).
                orElseThrow(()-> new IllegalArgumentException("La categoria " + request.categoriaId() + " no existe")) ;
        }
        producto.setCategoria(categoria);
    }
        
}
