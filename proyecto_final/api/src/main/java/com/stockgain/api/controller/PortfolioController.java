package com.stockgain.api.controller;

/**
 * PORTFOLIOCONTROLLER.JAVA
 * 
 * Controlador REST para gestión de portafolios de inversión en StockGain
 * 
 * Descripción:
 * Este controlador maneja todas las operaciones relacionadas con los portafolios
 * de inversión de los usuarios. Permite consultar posiciones, añadir nuevas
 * inversiones, actualizar posiciones existentes y vender activos.
 * 
 * Endpoints disponibles:
 * - GET /api/portfolio/{userId} - Obtener portafolio de un usuario
 * - POST /api/portfolio/add - Añadir o actualizar posición en portafolio
 * - DELETE /api/portfolio/sell/{posicionId} - Vender (eliminar) posición
 * 
 * Funcionalidades principales:
 * - Consulta de portafolios por usuario
 * - Adición de nuevas posiciones de inversión
 * - Actualización de posiciones existentes con precio promedio ponderado
 * - Eliminación de posiciones (venta de activos)
 * - Validación de existencia de usuarios, activos y posiciones
 * - Manejo transaccional para operaciones críticas
 * 
 * Lógica de negocio:
 * - Cálculo automático de precio promedio ponderado
 * - Acumulación de cantidad en posiciones existentes
 * - Validación de integridad de datos
 * - Manejo de errores con mensajes informativos
 * 
 * Integración:
 * - Usa PosicionRepository para gestión de posiciones
 * - Usa PortafolioRepository para acceso a portafolios
 * - Usa ActivoRepository para validación de activos
 * - Devuelve DTOs para optimizar transferencia de datos
 * 
 * @author StockGain Team
 * @version 1.0
 */

import com.stockgain.api.model.*;
import com.stockgain.api.repository.ActivoRepository;
import com.stockgain.api.repository.PortafolioRepository;
import com.stockgain.api.repository.PosicionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/portfolio")
@CrossOrigin(origins = "*")
public class PortfolioController {
    
    // Inyección de dependencias para repositories
    @Autowired
    private PosicionRepository posicionRepository;
    
    @Autowired
    private PortafolioRepository portafolioRepository;
    
    @Autowired
    private ActivoRepository activoRepository;
    
    /**
     * Obtiene el portafolio de un usuario específico
     * 
     * @param userId ID del usuario
     * @return ResponseEntity con lista de posiciones del portafolio
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<PosicionDTO>> getPortfolioByUserId(@PathVariable Integer userId) {
        // Filtrar posiciones por usuario y convertir a DTOs
        List<PosicionDTO> portfolio = posicionRepository.findAll().stream()
                .filter(p -> p.getPortafolio().getUsuario().getId_usuario().equals(userId))
                .map(PosicionDTO::fromEntity) // Mapeo más limpio usando un método estático
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(portfolio);
    }
    
    /**
     * Añade una nueva posición al portafolio o actualiza una existente
     * 
     * @param request datos de la posición a añadir
     * @return ResponseEntity con la posición creada/actualizada
     */
    @PostMapping("/add")
    @Transactional
    public ResponseEntity<?> addOrUpdatePosition(@RequestBody AddPositionRequest request) {
        // Buscar portafolio del usuario
        Optional<Portafolio> portafolioOpt = portafolioRepository.findByUsuarioId_usuario(request.getUserId()).stream().findFirst();
        if (portafolioOpt.isEmpty()) {
            return new ResponseEntity<>("Portafolio no encontrado para el usuario.", HttpStatus.NOT_FOUND);
        }
        
        // Buscar activo por ticker
        Optional<Activo> activoOpt = activoRepository.findByTicker(request.getTicker());
        if (activoOpt.isEmpty()) {
            return new ResponseEntity<>("Activo con el ticker '" + request.getTicker() + "' no fue encontrado.", HttpStatus.NOT_FOUND);
        }
        
        Portafolio portafolio = portafolioOpt.get();
        Activo activo = activoOpt.get();
        
        // Verificar si ya existe una posición para este activo
        Optional<Posicion> posicionExistenteOpt = posicionRepository.findByPortafolioAndActivo(portafolio, activo);
        
        Posicion posicionGuardada;
        
        if (posicionExistenteOpt.isPresent()) {
            // Actualizar posición existente con precio promedio ponderado
            Posicion posicionExistente = posicionExistenteOpt.get();
            
            // Obtener valores actuales
            BigDecimal cantidadVieja = posicionExistente.getCantidad();
            BigDecimal precioViejo = posicionExistente.getPrecioCompraPromedio();
            BigDecimal cantidadNueva = request.getCantidad();
            BigDecimal precioNuevo = request.getPrecioCompra();
            
            // Calcular nueva cantidad total
            BigDecimal cantidadTotal = cantidadVieja.add(cantidadNueva);
            
            // Calcular precio promedio ponderado
            BigDecimal valorViejo = cantidadVieja.multiply(precioViejo);
            BigDecimal valorNuevo = cantidadNueva.multiply(precioNuevo);
            BigDecimal precioPromedioPonderado = (valorViejo.add(valorNuevo)).divide(cantidadTotal, 4, BigDecimal.ROUND_HALF_UP);
            
            // Actualizar posición existente
            posicionExistente.setCantidad(cantidadTotal);
            posicionExistente.setPrecioCompraPromedio(precioPromedioPonderado);
            
            posicionGuardada = posicionRepository.save(posicionExistente);
        } else {
            // Crear nueva posición
            Posicion nuevaPosicion = new Posicion();
            nuevaPosicion.setPortafolio(portafolio);
            nuevaPosicion.setActivo(activo);
            nuevaPosicion.setCantidad(request.getCantidad());
            nuevaPosicion.setPrecioCompraPromedio(request.getPrecioCompra());
            nuevaPosicion.setFechaPrimeraCompra(new Date(System.currentTimeMillis()));
            
            posicionGuardada = posicionRepository.save(nuevaPosicion);
        }
        
        return new ResponseEntity<>(PosicionDTO.fromEntity(posicionGuardada), HttpStatus.OK);
    }
    
    /**
     * Vende (elimina) una posición del portafolio
     * 
     * @param posicionId ID de la posición a eliminar
     * @return ResponseEntity con mensaje de confirmación
     */
    @DeleteMapping("/sell/{posicionId}")
    @Transactional
    public ResponseEntity<?> sellPosition(@PathVariable Integer posicionId) {
        // Verificar que la posición existe
        Optional<Posicion> posicionOpt = posicionRepository.findById(posicionId);
        if (posicionOpt.isEmpty()) {
            return new ResponseEntity<>("Posición no encontrada.", HttpStatus.NOT_FOUND);
        }
        
        // Eliminar posición (venta completa)
        posicionRepository.deleteById(posicionId);
        
        return ResponseEntity.ok("Activo vendido (eliminado) correctamente.");
    }
}