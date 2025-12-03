package cl.backend.bienestarmental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsuarioDto {

    private String nombre;
    private String email;
    private String password;
}
