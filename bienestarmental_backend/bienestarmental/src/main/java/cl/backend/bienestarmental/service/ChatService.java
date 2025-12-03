package cl.backend.bienestarmental.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cl.backend.bienestarmental.dto.ChatDto;
import cl.backend.bienestarmental.dto.CreateMensajeDto;
import cl.backend.bienestarmental.dto.MensajeDto;
import cl.backend.bienestarmental.model.Chat;
import cl.backend.bienestarmental.model.Mensaje;
import cl.backend.bienestarmental.model.Usuario;
import cl.backend.bienestarmental.repository.ChatRepository;
import cl.backend.bienestarmental.repository.MensajeRepository;
import cl.backend.bienestarmental.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final MensajeRepository mensajeRepository;
    private final UsuarioRepository usuarioRepository;

    // Obtener chats del usuario
    public List<ChatDto> getChatsPorUsuario(String email) {
        List<Chat> chats = chatRepository.findByUsuario_Email(email);
        return chats.stream().map(this::toDto).collect(Collectors.toList());
    }

    // Crear chat si no existe
    public ChatDto iniciarChat(String email, String mensajeInicial) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));

        Chat chat = new Chat();
        chat.setUsuario(usuario);

        Chat saved = chatRepository.save(chat);

        // mensaje inicial
        Mensaje m = new Mensaje(saved, usuario, mensajeInicial);
        mensajeRepository.save(m);

        saved.getMensajes().add(m);

        return toDto(saved);
    }

    // Guardar mensaje del usuario
    public MensajeDto enviarMensaje(Long chatId, CreateMensajeDto dto, String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado"));

        Mensaje mensaje = new Mensaje(chat, usuario, dto.getContenido());
        Mensaje saved = mensajeRepository.save(mensaje);

        chat.getMensajes().add(saved);

        return toMensajeDto(saved);
    }

        public void eliminarChat(Long chatId, String email) {

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado"));

        if (!chat.getUsuario().getEmail().equals(email)) {
                throw new RuntimeException("No autorizado");
        }

        mensajeRepository.deleteAll(chat.getMensajes());
        chatRepository.delete(chat);
        }


    // Conversores
    private ChatDto toDto(Chat chat) {
        return new ChatDto(
                chat.getId(),
                chat.getUsuario().getId(),
                chat.getMensajes().stream().map(this::toMensajeDto).toList()
        );
    }

    private MensajeDto toMensajeDto(Mensaje m) {
        return new MensajeDto(
                m.getId(),
                m.getEmisor() != null ? m.getEmisor().getId() : null,
                m.getContenido(),
                m.getFechaEnvio()
        );
    }
}
