package cl.backend.bienestarmental.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.backend.bienestarmental.dto.ChatDto;
import cl.backend.bienestarmental.dto.CreateMensajeDto;
import cl.backend.bienestarmental.dto.MensajeDto;
import cl.backend.bienestarmental.service.ChatService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping
    public ResponseEntity<List<ChatDto>> getChatsDelUsuario(
            @RequestHeader("X-User-Email") String email) {

        return ResponseEntity.ok(chatService.getChatsPorUsuario(email));
    }

    @PostMapping
    public ResponseEntity<ChatDto> createChat(
            @RequestHeader("X-User-Email") String email,
            @RequestBody(required = false) Map<String, Object> body) {

        ChatDto chat = chatService.iniciarChat(email, "Hola! ¿En qué puedo ayudarte hoy?");
        return new ResponseEntity<>(chat, HttpStatus.CREATED);
    }

    @PostMapping("/{chatId}/mensajes")
    public ResponseEntity<MensajeDto> enviarMensaje(
            @PathVariable Long chatId,
            @RequestBody CreateMensajeDto dto,
            @RequestHeader("X-User-Email") String email) {

        return new ResponseEntity<>(chatService.enviarMensaje(chatId, dto, email), HttpStatus.CREATED);
    }

    @DeleteMapping("/{chatId}")
        public ResponseEntity<Void> eliminarChat(
                @PathVariable Long chatId,
                @RequestHeader("X-User-Email") String email) {

            chatService.eliminarChat(chatId, email);
            return ResponseEntity.noContent().build();
        }

}
