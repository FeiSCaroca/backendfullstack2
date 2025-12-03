package cl.backend.bienestarmental.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.backend.bienestarmental.config.security.JwtProvider;
import cl.backend.bienestarmental.dto.UsuarioDto;
import cl.backend.bienestarmental.model.Usuario;
import cl.backend.bienestarmental.repository.UsuarioRepository;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // 👈 Para encriptar
    private final JwtProvider jwtProvider;         // 👈 Para generar tokens reales

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtProvider jwtProvider) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    // ================================
    //           REGISTER
    // ================================
    public ResponseEntity<?> register(String nombre, String email, String password) {

        if (usuarioRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El correo ya está registrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        // 🔒 Encriptamos la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(password));

        usuarioRepository.save(usuario);

        // 🔑 Generamos un token JWT real
        String token = jwtProvider.generateTokenFromEmail(usuario.getEmail());

        Map<String, Object> resp = new HashMap<>();
        resp.put("token", token);
        resp.put("usuario", new UsuarioDto(usuario.getId(), usuario.getNombre(), usuario.getEmail()));

        return ResponseEntity.ok(resp);
    }

    // ================================
    //              LOGIN
    // ================================
    public ResponseEntity<?> login(String email, String password) {

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos.");
        }

        Usuario usuario = usuarioOpt.get();

        // 🔒 Verificamos la contraseña encriptada
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos.");
        }

        // 🔑 Generamos un token JWT real
        String token = jwtProvider.generateTokenFromEmail(usuario.getEmail());

        Map<String, Object> resp = new HashMap<>();
        resp.put("token", token);
        resp.put("usuario", new UsuarioDto(usuario.getId(), usuario.getNombre(), usuario.getEmail()));

        return ResponseEntity.ok(resp);
    }
}