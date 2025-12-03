package cl.backend.bienestarmental.service;

import cl.backend.bienestarmental.dto.ContactoEmergenciaDto;
import cl.backend.bienestarmental.model.ContactoEmergencia;
import cl.backend.bienestarmental.repository.ContactoEmergenciaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactoEmergenciaServiceTest {

    @Mock
    private ContactoEmergenciaRepository contactoRepository;

    @InjectMocks
    private ContactoEmergenciaService contactoService;

    @Test
    void testFindAll() {
        // Arrange
        ContactoEmergencia contacto = new ContactoEmergencia();
        contacto.setId(1L);
        contacto.setNombre("Línea de ayuda");
        contacto.setTelefono("123456789");

        when(contactoRepository.findAll()).thenReturn(Collections.singletonList(contacto));

        // Act
        List<ContactoEmergenciaDto> result = contactoService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Línea de ayuda", result.get(0).getNombre());
    }
}