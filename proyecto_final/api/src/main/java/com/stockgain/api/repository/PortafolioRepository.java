package com.stockgain.api.repository;
import com.stockgain.api.model.Portafolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface PortafolioRepository extends JpaRepository<Portafolio, Integer> {
    @Query("SELECT p FROM Portafolio p WHERE p.usuario.id_usuario = :userId")
    List<Portafolio> findByUsuarioId_usuario(@Param("userId") Integer userId);
}
