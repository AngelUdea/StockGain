package com.stockgain.api.repository;
import com.stockgain.api.model.Activo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface ActivoRepository extends JpaRepository<Activo, Integer> {
    Optional<Activo> findByTicker(String ticker);
}