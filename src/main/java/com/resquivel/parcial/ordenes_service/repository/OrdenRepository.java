package com.resquivel.parcial.ordenes_service.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.resquivel.parcial.ordenes_service.model.Orden;

@Repository
public interface OrdenRepository extends MongoRepository<Orden, String> {
    List<Orden> findByUsuarioId(String usuarioId);
}