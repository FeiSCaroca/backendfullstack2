package cl.backend.bienestarmental.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.backend.bienestarmental.dto.CreateDiarioEntryDto;
import cl.backend.bienestarmental.dto.DiarioEntryDto;
import cl.backend.bienestarmental.dto.EstadoAnimoDto;
import cl.backend.bienestarmental.model.DiarioEntry;
import cl.backend.bienestarmental.model.EstadoAnimo;
import cl.backend.bienestarmental.model.Usuario;
import cl.backend.bienestarmental.repository.DiarioEntryRepository;
import cl.backend.bienestarmental.repository.EstadoAnimoRepository;
import cl.backend.bienestarmental.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiarioService {

    private final DiarioEntryRepository diarioEntryRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstadoAnimoRepository estadoAnimoRepository;

    // ------------------------------------------------------------------------
    // Obtener entradas por usuario
    // ------------------------------------------------------------------------
    public List<DiarioEntryDto> obtenerEntradas(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<DiarioEntry> entries =
                diarioEntryRepository.findByUsuarioId(usuario.getId());

        return entries.stream()
                .map(this::mapToDto)
                .toList();
    }

    // ------------------------------------------------------------------------
    // Crear entrada
    // ------------------------------------------------------------------------
    public DiarioEntryDto crearEntrada(String email, CreateDiarioEntryDto dto) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        EstadoAnimo estado = estadoAnimoRepository.findById(dto.getEmotionId())
                .orElseThrow(() -> new RuntimeException("Estado de ánimo no encontrado"));

        DiarioEntry entry = new DiarioEntry(
                null,
                usuario,
                estado,
                dto.getThoughts(),
                LocalDateTime.now()
        );

        diarioEntryRepository.save(entry);

        return mapToDto(entry);
    }

    // ------------------------------------------------------------------------
    // Eliminar entrada
    // ------------------------------------------------------------------------
    public void eliminarEntrada(String email, Long id) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        DiarioEntry entry = diarioEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrada no encontrada"));

        if (!entry.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("No autorizado");
        }

        diarioEntryRepository.delete(entry);
    }

    // ------------------------------------------------------------------------
    // Mapper
    // ------------------------------------------------------------------------
    private DiarioEntryDto mapToDto(DiarioEntry entry) {

        EstadoAnimo estado = entry.getEstadoAnimo();

        EstadoAnimoDto estadoDto = new EstadoAnimoDto(
                estado.getId(),
                estado.getNombre(),
                estado.getEmoji(),
                estado.getColor()
        );

        return new DiarioEntryDto(
                entry.getId(),
                estadoDto,
                entry.getThoughts(),
                entry.getFechaCreacion().toString()
        );
    }
}
