package cl.backend.bienestarmental.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.backend.bienestarmental.dto.CreateDiarioEntryDto;
import cl.backend.bienestarmental.dto.DiarioEntryDto;
import cl.backend.bienestarmental.service.DiarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/diario")
@RequiredArgsConstructor
public class DiarioController {

    private final DiarioService diarioService;

    // =====================================================
    // OBTENER TODAS LAS ENTRADAS DEL USUARIO
    // =====================================================
    @GetMapping
    public ResponseEntity<List<DiarioEntryDto>> obtener(
            @RequestHeader("X-User-Email") String email) {

        System.out.println("📥 Email recibido en DiarioController: " + email);

        return ResponseEntity.ok(diarioService.obtenerEntradas(email));
    }


    // =====================================================
    // CREAR UNA NUEVA ENTRADA
    // =====================================================
    @PostMapping
    public ResponseEntity<DiarioEntryDto> crearEntrada(
            @RequestHeader("X-User-Email") String email,
            @RequestBody CreateDiarioEntryDto dto) {

        return ResponseEntity.ok(diarioService.crearEntrada(email, dto));
    }

    // =====================================================
    // ELIMINAR UNA ENTRADA
    // =====================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEntrada(
            @RequestHeader("X-User-Email") String email,
            @PathVariable Long id) {

        diarioService.eliminarEntrada(email, id);
        return ResponseEntity.noContent().build();
    }
}
