package com.resquivel.parcial.ordenes_service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resquivel.parcial.ordenes_service.model.Orden;
import com.resquivel.parcial.ordenes_service.repository.OrdenRepository;
// Asegúrate de importar tu CloudWatchService si está en otra carpeta
// import com.resquivel.parcial.ordenes_service.service.CloudWatchService; 

@RestController
@RequestMapping("/ordenes")
public class OrdenController {

    private static final Logger logger = LoggerFactory.getLogger(OrdenController.class);

    @Autowired
    private OrdenRepository ordenRepository;

    // 1. Aquí inyectamos el nuevo conector directo de AWS
    @Autowired
    private CloudWatchService cloudWatchService;

    @GetMapping
    public List<Orden> obtenerOrdenes() {
        logger.info("Solicitud recibida: Consultando todas las órdenes");
        return ordenRepository.findAll();
    }

    @PostMapping
    public Orden crearOrden(@RequestBody Orden orden) {
        orden.setEstado("CREADA"); // Toda orden nueva entra como "CREADA" por defecto
        
        // El log local de consola (se queda por si acaso)
        logger.info("Solicitud recibida: Creando orden para el producto ID: {}", orden.getProductoId());
        
        // 2. Aquí disparamos el mensaje directamente hacia LocalStack
        cloudWatchService.enviarLog("¡Éxito! Procesando nueva orden para el producto: " + orden.getProductoId());
        
        return ordenRepository.save(orden);
    }
}
