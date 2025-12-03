package cl.backend.bienestarmental.service;

import cl.backend.bienestarmental.dto.ContactoEmergenciaDto;
import cl.backend.bienestarmental.model.ContactoEmergencia;
import cl.backend.bienestarmental.repository.ContactoEmergenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactoEmergenciaService {

    @Autowired
    private ContactoEmergenciaRepository contactoRepository;

    public List<ContactoEmergenciaDto> findAll() {
        return contactoRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ContactoEmergenciaDto mapToDto(ContactoEmergencia contacto) {
        return new ContactoEmergenciaDto(
                contacto.getId(),
                contacto.getNombre(),
                contacto.getTelefono(),
                contacto.getDescripcion()
        );
    }
}