package cl.backend.bienestarmental.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.backend.bienestarmental.dto.EstadoAnimoDto;
import cl.backend.bienestarmental.service.EstadoAnimoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/estado-animo")
@RequiredArgsConstructor
public class EstadoAnimoController {

    private final EstadoAnimoService estadoAnimoService;

    @GetMapping
    public ResponseEntity<List<EstadoAnimoDto>> listar() {
        return ResponseEntity.ok(estadoAnimoService.listar());
    }
}
