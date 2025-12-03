package cl.backend.bienestarmental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.backend.bienestarmental.model.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    // buscar por objeto usuario (id)
    List<Chat> findByUsuarioId(Long usuarioId);

    // buscar por email (usado actualmente por tu ChatService)
    List<Chat> findByUsuario_Email(String email);
}
