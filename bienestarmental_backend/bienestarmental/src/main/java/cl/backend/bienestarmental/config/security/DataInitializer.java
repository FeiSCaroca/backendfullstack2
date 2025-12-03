package cl.backend.bienestarmental.config.security;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import cl.backend.bienestarmental.model.ContactoEmergencia;
import cl.backend.bienestarmental.model.EstadoAnimo;
import cl.backend.bienestarmental.model.Usuario;
import cl.backend.bienestarmental.repository.ContactoEmergenciaRepository;
import cl.backend.bienestarmental.repository.EstadoAnimoRepository;
import cl.backend.bienestarmental.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EstadoAnimoRepository estadoAnimoRepository;
    private final ContactoEmergenciaRepository contactoEmergenciaRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Verificando datos iniciales...");

        // --------------------------------------------------------------------
        // CREAR ADMIN SI NO EXISTE
        // --------------------------------------------------------------------
        if (usuarioRepository.count() == 0) {

            Usuario admin = new Usuario();
            admin.setNombre("Admin");
            admin.setEmail("admin@bienestarmental.cl");
            admin.setPassword(passwordEncoder.encode("admin123"));
            usuarioRepository.save(admin);

            System.out.println("Usuario admin creado.");
        }

        // --------------------------------------------------------------------
        // ESTADOS DE ÁNIMO (solo si no existen)
        // --------------------------------------------------------------------
        if (estadoAnimoRepository.count() == 0) {

            estadoAnimoRepository.save(new EstadoAnimo(null, "Alegría", "😊", "#B8D8C0"));
            estadoAnimoRepository.save(new EstadoAnimo(null, "Tristeza", "😔", "#A9C5E8"));
            estadoAnimoRepository.save(new EstadoAnimo(null, "Rabia", "😡", "#F6B8B8"));
            estadoAnimoRepository.save(new EstadoAnimo(null, "Ansiedad", "😰", "#F3E2A9"));
            estadoAnimoRepository.save(new EstadoAnimo(null, "Calma", "😌", "#D7EFD9"));

            System.out.println("Estados de ánimo insertados.");
        }

        // --------------------------------------------------------------------
        // CONTACTOS DE EMERGENCIA (solo si no existen)
        // --------------------------------------------------------------------
        if (contactoEmergenciaRepository.count() == 0) {

            ContactoEmergencia contacto1 = new ContactoEmergencia();
            contacto1.setNombre("SAMU (Emergencias Médicas)");
            contacto1.setTelefono("131");
            contacto1.setDescripcion("Atención de emergencias médicas en todo Chile.");

            ContactoEmergencia contacto2 = new ContactoEmergencia();
            contacto2.setNombre("Salud Responde");
            contacto2.setTelefono("600 360 7777");
            contacto2.setDescripcion("Línea de ayuda del Ministerio de Salud de Chile (orientación en salud y apoyo psicológico).");

            ContactoEmergencia contacto3 = new ContactoEmergencia();
            contacto3.setNombre("Carabineros (Emergencia Policial)");
            contacto3.setTelefono("133");
            contacto3.setDescripcion("Emergencias de seguridad y orden público.");

            contactoEmergenciaRepository.saveAll(List.of(contacto1, contacto2, contacto3));

            System.out.println("Contactos de emergencia insertados.");
        }

        System.out.println("Inicialización completada.");
    }
}
