package com.stockgain.api.controller;
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
    @Autowired
    private PosicionRepository posicionRepository;
    @Autowired
    private PortafolioRepository portafolioRepository;
    @Autowired
    private ActivoRepository activoRepository;
    @GetMapping("/{userId}")
    public ResponseEntity<List<PosicionDTO>> getPortfolioByUserId(@PathVariable Integer userId) {
        List<PosicionDTO> portfolio = posicionRepository.findAll().stream()
                .filter(p -> p.getPortafolio().getUsuario().getId_usuario().equals(userId))
                .map(PosicionDTO::fromEntity) // Mapeo más limpio usando un método estático
                .collect(Collectors.toList());
        return ResponseEntity.ok(portfolio);
    }
    @PostMapping("/add")
    @Transactional
    public ResponseEntity<?> addOrUpdatePosition(@RequestBody AddPositionRequest request) {
        Optional<Portafolio> portafolioOpt = portafolioRepository.findByUsuarioId_usuario(request.getUserId()).stream().findFirst();
        if (portafolioOpt.isEmpty()) {
            return new ResponseEntity<>("Portafolio no encontrado para el usuario.", HttpStatus.NOT_FOUND);
        }
        Optional<Activo> activoOpt = activoRepository.findByTicker(request.getTicker());
        if (activoOpt.isEmpty()) {
            return new ResponseEntity<>("Activo con el ticker '" + request.getTicker() + "' no fue encontrado.", HttpStatus.NOT_FOUND);
        }
        Portafolio portafolio = portafolioOpt.get();
        Activo activo = activoOpt.get();
        Optional<Posicion> posicionExistenteOpt = posicionRepository.findByPortafolioAndActivo(portafolio, activo);
        Posicion posicionGuardada;
        if (posicionExistenteOpt.isPresent()) {
            Posicion posicionExistente = posicionExistenteOpt.get();
            BigDecimal cantidadVieja = posicionExistente.getCantidad();
            BigDecimal precioViejo = posicionExistente.getPrecioCompraPromedio();
            BigDecimal cantidadNueva = request.getCantidad();
            BigDecimal precioNuevo = request.getPrecioCompra();
            BigDecimal cantidadTotal = cantidadVieja.add(cantidadNueva);
            BigDecimal valorViejo = cantidadVieja.multiply(precioViejo);
            BigDecimal valorNuevo = cantidadNueva.multiply(precioNuevo);
            BigDecimal precioPromedioPonderado = (valorViejo.add(valorNuevo)).divide(cantidadTotal, 4, BigDecimal.ROUND_HALF_UP);
            posicionExistente.setCantidad(cantidadTotal);
            posicionExistente.setPrecioCompraPromedio(precioPromedioPonderado);
            posicionGuardada = posicionRepository.save(posicionExistente);
        } else {
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
    @DeleteMapping("/sell/{posicionId}")
    @Transactional
    public ResponseEntity<?> sellPosition(@PathVariable Integer posicionId) {
        Optional<Posicion> posicionOpt = posicionRepository.findById(posicionId);
        if (posicionOpt.isEmpty()) {
            return new ResponseEntity<>("Posición no encontrada.", HttpStatus.NOT_FOUND);
        }
        posicionRepository.deleteById(posicionId);
        return ResponseEntity.ok("Activo vendido (eliminado) correctamente.");
    }
}