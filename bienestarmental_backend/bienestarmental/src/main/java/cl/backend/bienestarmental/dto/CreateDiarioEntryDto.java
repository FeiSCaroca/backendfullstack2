package cl.backend.bienestarmental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDiarioEntryDto {

    private Long emotionId;  // era humorId
    private String thoughts; // era contenido
}
