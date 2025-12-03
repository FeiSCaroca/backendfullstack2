package cl.backend.bienestarmental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.backend.bienestarmental.model.Mensaje;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
}
