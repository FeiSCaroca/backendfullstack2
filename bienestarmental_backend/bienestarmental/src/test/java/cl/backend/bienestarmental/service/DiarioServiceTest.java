package cl.backend.bienestarmental.service;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cl.backend.bienestarmental.dto.CreateDiarioEntryDto;
import cl.backend.bienestarmental.model.DiarioEntry;
import cl.backend.bienestarmental.model.EstadoAnimo;
import cl.backend.bienestarmental.model.Usuario;
import cl.backend.bienestarmental.repository.DiarioEntryRepository;
import cl.backend.bienestarmental.repository.EstadoAnimoRepository;
import cl.backend.bienestarmental.repository.UsuarioRepository;

public class DiarioServiceTest {

    @Test
    void crearEntrada_ok() {

        DiarioEntryRepository diarioRepo = mock(DiarioEntryRepository.class);
        UsuarioRepository usuarioRepo = mock(UsuarioRepository.class);
        EstadoAnimoRepository estadoRepo = mock(EstadoAnimoRepository.class);

        DiarioService service = new DiarioService(diarioRepo, usuarioRepo, estadoRepo);

        Usuario usuario = new Usuario(1L, "Felipe", "felipe@test.com", "123", null, null);
        EstadoAnimo estado = new EstadoAnimo(1L, "Feliz", "😊", "#FFF");

        when(usuarioRepo.findByEmail("felipe@test.com")).thenReturn(java.util.Optional.of(usuario));
        when(estadoRepo.findById(1L)).thenReturn(java.util.Optional.of(estado));

        CreateDiarioEntryDto dto = new CreateDiarioEntryDto(1L, "Hoy me siento bien");

        DiarioEntry saved = new DiarioEntry(1L, usuario, estado, "Hoy me siento bien", LocalDateTime.now());
        when(diarioRepo.save(any())).thenReturn(saved);

        var result = service.crearEntrada("felipe@test.com", dto);

        assertEquals("Hoy me siento bien", result.getThoughts());
        assertEquals(1L, result.getEmotion().getId());
    }
}
