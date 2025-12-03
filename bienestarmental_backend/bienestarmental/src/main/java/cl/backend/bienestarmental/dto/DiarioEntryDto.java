package cl.backend.bienestarmental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiarioEntryDto {

    private Long id;

    private EstadoAnimoDto emotion; // <--- ESTA ES LA CORRECCIÓN

    private String thoughts;

    private String date;
}
