package cl.backend.bienestarmental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.backend.bienestarmental.model.EstadoAnimo;

@Repository
public interface EstadoAnimoRepository extends JpaRepository<EstadoAnimo, Long> {
}