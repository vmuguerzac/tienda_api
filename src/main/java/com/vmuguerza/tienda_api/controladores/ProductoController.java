package com.vmuguerza.tienda_api.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vmuguerza.tienda_api.dto.PaginaResponse;
import com.vmuguerza.tienda_api.dto.ProductoRequest;
import com.vmuguerza.tienda_api.dto.ProductoResponse;
import com.vmuguerza.tienda_api.modelo.Producto;
import com.vmuguerza.tienda_api.servicios.ProductoService;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;





@RestController 
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService productoService;
    // Inyectar el servicio
    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerPorId(@PathVariable Long id){
        return productoService.buscarProductoPorId(id)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 400
    }

    // Create
    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@RequestBody ProductoRequest request) {
        ProductoResponse productoCreado = productoService.crearProducto(request);
        URI ubicacion = URI.create("/api/productos/" + productoCreado.id());
        return ResponseEntity.created(ubicacion).body(productoCreado);
    }
    
    // Update
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(@PathVariable Long id, 
            @RequestBody ProductoRequest request
    ) {
        return productoService.actualizarProducto(id, request)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        return productoService.eliminarProducto(id)
            ? ResponseEntity.noContent().build() // 204
            : ResponseEntity.notFound().build(); // 404
    }

    @GetMapping(params = "nombre")
    public PaginaResponse<ProductoResponse> buscarPorNombre(
            @RequestParam(required = false) String nombre,
            @PageableDefault(size = 8, sort = "id") Pageable pageable
    ) {
        return productoService.listarPaginando(nombre, pageable);
    }

    @GetMapping
    public PaginaResponse<ProductoResponse> buscarTodos(
        @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return productoService.listarPaginando("", pageable);
    }
    
    
}