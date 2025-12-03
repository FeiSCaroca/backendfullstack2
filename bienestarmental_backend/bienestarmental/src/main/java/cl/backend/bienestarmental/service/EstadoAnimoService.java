package cl.backend.bienestarmental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.backend.bienestarmental.dto.EstadoAnimoDto;
import cl.backend.bienestarmental.model.EstadoAnimo;
import cl.backend.bienestarmental.repository.EstadoAnimoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstadoAnimoService {

    private final EstadoAnimoRepository estadoAnimoRepository;

    public List<EstadoAnimoDto> listar() {
        return estadoAnimoRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private EstadoAnimoDto mapToDto(EstadoAnimo e) {
        return new EstadoAnimoDto(
                e.getId(),
                e.getNombre(),
                e.getEmoji(),
                e.getColor()
        );
    }
}
