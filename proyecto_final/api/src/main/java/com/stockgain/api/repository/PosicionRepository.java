package com.stockgain.api.repository;
import com.stockgain.api.model.Activo;
import com.stockgain.api.model.Portafolio;
import com.stockgain.api.model.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface PosicionRepository extends JpaRepository<Posicion, Integer> {
    Optional<Posicion> findByPortafolioAndActivo(Portafolio portafolio, Activo activo);
}