package com.resquivel.parcial.ordenes_service.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.resquivel.parcial.ordenes_service.model.Orden;
import com.resquivel.parcial.ordenes_service.repository.OrdenRepository;

@RestController
@RequestMapping("/ordenes")
public class OrdenController {

    private static final Logger logger = LoggerFactory.getLogger(OrdenController.class);

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private CloudWatchService cloudWatchService;

    // POST /ordenes - Crear una orden
    @PostMapping
    public Orden crearOrden(@RequestBody Orden orden) {
        logger.info("Creando nueva orden para el producto: {}", orden.getProductoId());
        if (orden.getEstado() == null) {
            orden.setEstado("CREADA");
        }
        cloudWatchService.enviarLog("Nueva orden creada: Producto " + orden.getProductoId());
        return ordenRepository.save(orden);
    }

    // GET /ordenes/{id} - Detalle de una orden
    @GetMapping("/{id}")
    public org.springframework.http.ResponseEntity<Orden> obtenerOrden(@PathVariable("id") String id) {
        logger.info("Consultando detalle de orden ID: {}", id);
        return ordenRepository.findById(id)
                .map(orden -> org.springframework.http.ResponseEntity.ok(orden))
                .orElse(org.springframework.http.ResponseEntity.notFound().build());
    }

    // GET /ordenes/usuario/{usuarioId} - Órdenes de un usuario específico
    @GetMapping("/usuario/{usuarioId}")
    public List<Orden> obtenerOrdenesPorUsuario(@PathVariable("usuarioId") String usuarioId) {
        logger.info("Consultando órdenes del usuario ID: {}", usuarioId);
        return ordenRepository.findByUsuarioId(usuarioId);
    }

    // PUT /ordenes/{id}/status - Cambiar estado de la orden
    @PutMapping("/{id}/status")
    public org.springframework.http.ResponseEntity<Orden> actualizarEstado(@PathVariable("id") String id, @RequestBody String nuevoEstado) {
        logger.info("Actualizando estado de la orden {} a {}", id, nuevoEstado);
        return ordenRepository.findById(id).map(orden -> {
            orden.setEstado(nuevoEstado);
            try {
                cloudWatchService.enviarLog("Estado de orden actualizado a: " + nuevoEstado);
            } catch (Exception e) {}
            return org.springframework.http.ResponseEntity.ok(ordenRepository.save(orden));
        }).orElse(org.springframework.http.ResponseEntity.notFound().build());
    }
}