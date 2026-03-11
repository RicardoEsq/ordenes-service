package com.resquivel.parcial.ordenes_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.resquivel.parcial.ordenes_service.model.Orden; // <-- Ajusta tu paquete aquí

@Repository
public interface OrdenRepository extends MongoRepository<Orden, String> {
}
