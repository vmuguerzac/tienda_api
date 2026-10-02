package com.vmuguerza.tienda_api.dto;

import java.math.BigDecimal;

// Lo que el cliente envia POST, PUT
public record ProductoRequest(
    String nombreProducto,
    String descripcion,
    BigDecimal precio,
    boolean sinStock,
    String imagenProducto,
    Long categoriaId
){}
