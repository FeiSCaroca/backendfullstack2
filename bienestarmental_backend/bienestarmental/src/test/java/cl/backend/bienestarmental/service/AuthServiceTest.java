package cl.backend.bienestarmental.service;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity; 
import org.springframework.security.crypto.password.PasswordEncoder;

import cl.backend.bienestarmental.config.security.JwtProvider;
import cl.backend.bienestarmental.dto.LoginUsuarioDto;
import cl.backend.bienestarmental.dto.RegistroUsuarioDto;
import cl.backend.bienestarmental.dto.UsuarioDto;
import cl.backend.bienestarmental.model.Usuario;
import cl.backend.bienestarmental.repository.UsuarioRepository;

class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthService authService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Felipe");
        usuario.setEmail("felipe@test.com");
        usuario.setPassword("encodedpass");
    }

    // ------------------------------
    // TEST REGISTER
    // ------------------------------
    @Test
    void testRegisterSuccess() {

        RegistroUsuarioDto request = new RegistroUsuarioDto("Felipe", "felipe@test.com", "1234");

        // Configurar Mocks
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedpass");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(jwtProvider.generateTokenFromEmail(anyString())).thenReturn("fake-jwt-token");

        // EJECUCIÓN: Pasamos los Strings sueltos, no el objeto DTO
        ResponseEntity<?> response = authService.register(
            request.getNombre(), 
            request.getEmail(), 
            request.getPassword()
        );

        // VERIFICACIÓN
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());

        // Extraemos el mapa del cuerpo de la respuesta
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals("fake-jwt-token", body.get("token"));

        UsuarioDto usuarioDto = (UsuarioDto) body.get("usuario");
        assertEquals("Felipe", usuarioDto.getNombre());
        assertEquals("felipe@test.com", usuarioDto.getEmail());
    }

    // ------------------------------
    // TEST LOGIN
    // ------------------------------
    @Test
    void testLoginSuccess() {

        LoginUsuarioDto request = new LoginUsuarioDto("felipe@test.com", "1234");

        // Configurar Mocks
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtProvider.generateTokenFromEmail(anyString())).thenReturn("jwt-token");

        // EJECUCIÓN: Pasamos los Strings sueltos
        ResponseEntity<?> response = authService.login(
            request.getEmail(), 
            request.getPassword()
        );

        // VERIFICACIÓN
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());

        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals("jwt-token", body.get("token"));
        
        UsuarioDto usuarioDto = (UsuarioDto) body.get("usuario");
        assertEquals("Felipe", usuarioDto.getNombre());
        assertEquals("felipe@test.com", usuarioDto.getEmail());
    }
}