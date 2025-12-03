package cl.backend.bienestarmental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactoEmergenciaDto {

    private Long id;
    private String nombre;
    private String telefono;
    private String descripcion;
}