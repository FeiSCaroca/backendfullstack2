package cl.backend.bienestarmental.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // 👈 Importante
import org.springframework.web.bind.annotation.PutMapping; // 👈 Importante
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.backend.bienestarmental.dto.LoginUsuarioDto;
import cl.backend.bienestarmental.dto.RegistroUsuarioDto;
import cl.backend.bienestarmental.dto.UpdateUsuarioDto;
import cl.backend.bienestarmental.dto.UsuarioDto;
import cl.backend.bienestarmental.model.Usuario;
import cl.backend.bienestarmental.repository.UsuarioRepository;
import cl.backend.bienestarmental.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService,
                          UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    
    //REGISTER
 
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistroUsuarioDto dto) {
        // Extraemos los datos del DTO
        return authService.register(dto.getNombre(), dto.getEmail(), dto.getPassword());
    }

    
    //LOGIN
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUsuarioDto dto) {
        // Extraemos los datos del DTO
        return authService.login(dto.getEmail(), dto.getPassword());
    }

    
    //OBTENER PERFIL DEL USUARIO
    @GetMapping("/profile")
    public ResponseEntity<UsuarioDto> getProfile(@RequestHeader("X-User-Email") String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Usuario usuario = usuarioOpt.get();
        UsuarioDto dto = new UsuarioDto(usuario.getId(), usuario.getNombre(), usuario.getEmail());

        return ResponseEntity.ok(dto);
    }

    //ACTUALIZAR PERFIL
    
    @PutMapping("/profile")
    public ResponseEntity<UsuarioDto> updateProfile(
            @RequestHeader("X-User-Email") String email,
            @RequestBody UpdateUsuarioDto dto) { // 👈 CAMBIO AQUÍ: Usamos el DTO

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Usuario usuario = usuarioOpt.get();

        // 1. Actualizar nombre (si viene en el DTO)
        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            usuario.setNombre(dto.getNombre());
        }

        // 2. Actualizar password (si viene en el DTO)
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(new UsuarioDto(usuario.getId(), usuario.getNombre(), usuario.getEmail()));
    }

    
    // ELIMINAR CUENTA

    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteAccount(@RequestHeader("X-User-Email") String email) {
        Optional<Usuario> u = usuarioRepository.findByEmail(email);
        if (u.isPresent()) {
            usuarioRepository.delete(u.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    
    // OBTENER TODOS LOS USUARIOS
    
    @GetMapping("/users")
    public ResponseEntity<List<UsuarioDto>> getAllUsers() {
        List<UsuarioDto> usuarios = usuarioRepository.findAll()
                .stream()
                .map(u -> new UsuarioDto(u.getId(), u.getNombre(), u.getEmail()))
                .toList();
        
        return ResponseEntity.ok(usuarios);
    }
}