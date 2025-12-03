package cl.backend.bienestarmental.dto;

import java.util.List;

import cl.backend.bienestarmental.model.Chat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {

    private Long id;
    private Long usuarioId;
    private List<MensajeDto> mensajes;

    // 👇 NECESARIO PARA EL SERVICE
    public ChatDto(Chat c, List<MensajeDto> mensajes) {
        this.id = c.getId();
        this.usuarioId = c.getUsuario().getId();
        this.mensajes = mensajes;
    }
}
