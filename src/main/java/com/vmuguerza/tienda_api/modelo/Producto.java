package com.vmuguerza.tienda_api.modelo;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "productos")
public class Producto {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombreProducto;
    @Column(nullable = false)
    private String descripcion;
    @Column(precision=10, scale=2)
    private BigDecimal precio;
    private boolean sinStock;
    private String imagenProducto;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id") 
    private Categoria categoria;

    public Producto(){

    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombreProducto() {
        return nombreProducto;
    }
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public BigDecimal getPrecio() {
        return precio;
    }
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    public boolean isSinStock() {
        return sinStock;
    }
    public void setSinStock(boolean sinStock) {
        this.sinStock = sinStock;
    }
    public String getImagenProducto() {
        return imagenProducto;
    }
    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    
}
