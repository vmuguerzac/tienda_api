package com.vmuguerza.tienda_api.dto;

import java.math.BigDecimal;

public record ProductoResponse(
    Long id,
    String nombreProducto,
    String descripcion,
    BigDecimal precio,
    boolean sinStock,
    String imagenProducto,
    Long categoriaId
){}
