package cl.backend.bienestarmental.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import cl.backend.bienestarmental.dto.ChatDto;
import cl.backend.bienestarmental.dto.CreateMensajeDto;
import cl.backend.bienestarmental.dto.MensajeDto;
import cl.backend.bienestarmental.model.Chat;
import cl.backend.bienestarmental.model.Mensaje;
import cl.backend.bienestarmental.model.Usuario;
import cl.backend.bienestarmental.repository.ChatRepository;
import cl.backend.bienestarmental.repository.MensajeRepository;
import cl.backend.bienestarmental.repository.UsuarioRepository;

public class ChatServiceTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private MensajeRepository mensajeRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ChatService chatService;

    private Usuario usuario;
    private Chat chat;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("test@correo.com");

        chat = new Chat();
        chat.setId(10L);
        chat.setUsuario(usuario);
        chat.setMensajes(new ArrayList<>());
    }

    @Test
    void testIniciarChat() {
        when(usuarioRepository.findByEmail("test@correo.com"))
                .thenReturn(Optional.of(usuario));

        when(chatRepository.save(any(Chat.class)))
                .thenReturn(chat);

        when(mensajeRepository.save(any(Mensaje.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ChatDto resultado = chatService.iniciarChat("test@correo.com", "Hola");

        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals(1L, resultado.getUsuarioId());
        assertNotNull(resultado.getMensajes());
    }

    @Test
    void testEnviarMensaje() {
        CreateMensajeDto dto = new CreateMensajeDto();
        dto.setContenido("Hola IA");

        when(usuarioRepository.findByEmail("test@correo.com"))
                .thenReturn(Optional.of(usuario));

        when(chatRepository.findById(10L))
                .thenReturn(Optional.of(chat));

        when(mensajeRepository.save(any(Mensaje.class)))
                .thenAnswer(invocation -> {
                    Mensaje m = invocation.getArgument(0);
                    m.setId(99L);
                    return m;
                });

        MensajeDto resultado = chatService.enviarMensaje(10L, dto, "test@correo.com");

        assertNotNull(resultado);
        assertEquals(99L, resultado.getId());
        assertEquals(1L, resultado.getEmisorId());
        assertEquals("Hola IA", resultado.getContenido());
    }

    @Test
    void testGetChatsPorUsuario() {
        List<Chat> chats = List.of(chat);

        when(chatRepository.findByUsuario_Email("test@correo.com"))
                .thenReturn(chats);

        List<ChatDto> resultado = chatService.getChatsPorUsuario("test@correo.com");

        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getId());
        assertEquals(1L, resultado.get(0).getUsuarioId());
    }
}
