package com.resquivel.parcial.ordenes_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ordenes")
public class Orden {

    @Id
    private String id;
    private String productoId; // Aquí guardaremos el ID larguísimo que te dio Mongo
    private Integer cantidad;
    private String estado; // Ej: "CREADA", "PAGADA"

    public Orden() {}

    public Orden(String productoId, Integer cantidad, String estado) {
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.estado = estado;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
