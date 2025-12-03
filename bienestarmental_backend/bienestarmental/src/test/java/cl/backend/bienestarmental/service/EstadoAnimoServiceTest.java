package cl.backend.bienestarmental.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cl.backend.bienestarmental.dto.EstadoAnimoDto;
import cl.backend.bienestarmental.model.EstadoAnimo;
import cl.backend.bienestarmental.repository.EstadoAnimoRepository;

public class EstadoAnimoServiceTest {

    @Test
    void listarEstados_ok() {

        // Mock repositorio
        EstadoAnimoRepository repo = mock(EstadoAnimoRepository.class);

        // Servicio real usando el mock
        EstadoAnimoService service = new EstadoAnimoService(repo);

        // Datos simulados
        EstadoAnimo e1 = new EstadoAnimo(1L, "Feliz", "😊", "#FFD");
        EstadoAnimo e2 = new EstadoAnimo(2L, "Triste", "😢", "#AAB");

        when(repo.findAll()).thenReturn(List.of(e1, e2));

        // Llamada al método REAL
        List<EstadoAnimoDto> estados = service.listar();

        assertEquals(2, estados.size());
        assertEquals("Feliz", estados.get(0).getNombre());
        assertEquals("😢", estados.get(1).getEmoji());
    }
}
