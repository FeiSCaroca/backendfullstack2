package cl.backend.bienestarmental.dto;

import java.time.LocalDateTime;

import cl.backend.bienestarmental.model.Mensaje;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MensajeDto {

    private Long id;
    private Long emisorId;
    private String contenido;
    private LocalDateTime fechaEnvio;

    public MensajeDto(Mensaje m) {
        this.id = m.getId();
        this.emisorId = (m.getEmisor() != null ? m.getEmisor().getId() : null);
        this.contenido = m.getContenido();
        this.fechaEnvio = m.getFechaEnvio();
    }
}
