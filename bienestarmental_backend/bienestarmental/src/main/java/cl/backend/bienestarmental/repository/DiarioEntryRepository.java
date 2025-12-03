package cl.backend.bienestarmental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.backend.bienestarmental.model.DiarioEntry;

public interface DiarioEntryRepository extends JpaRepository<DiarioEntry, Long> {

    List<DiarioEntry> findByUsuarioId(Long usuarioId);
}
