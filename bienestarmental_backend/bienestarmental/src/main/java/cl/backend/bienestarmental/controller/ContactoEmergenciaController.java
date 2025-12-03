package cl.backend.bienestarmental.controller;

import cl.backend.bienestarmental.dto.ContactoEmergenciaDto;
import cl.backend.bienestarmental.service.ContactoEmergenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/emergencia")
@Tag(name = "4. Controlador de Emergencia", description = "Endpoints para obtener contactos de emergencia.")
public class ContactoEmergenciaController {

    @Autowired
    private ContactoEmergenciaService contactoService;

    @GetMapping
    @Operation(summary = "Obtener todos los contactos de emergencia")
    public ResponseEntity<List<ContactoEmergenciaDto>> getAllContactos() {
        List<ContactoEmergenciaDto> contactos = contactoService.findAll();
        return ResponseEntity.ok(contactos);
    }
}