package cl.backend.bienestarmental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUsuarioDto {
    private String nombre;
    private String password;
}