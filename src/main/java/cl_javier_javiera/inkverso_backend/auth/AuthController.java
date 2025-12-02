package cl_javier_javiera.inkverso_backend.auth;

import cl_javier_javiera.inkverso_backend.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        return repo.findByEmail(email)
                .filter(u -> Boolean.TRUE.equals(u.getActive())) 
                .filter(u -> passwordEncoder.matches(password, u.getPassword())) 
                .map(u -> ResponseEntity.ok(Map.of(
                        "message", "Login exitoso",
                        "role", u.getRole(),
                        "email", u.getEmail()
                )))
                .orElse(ResponseEntity.status(401).body(Map.of("message", "Credenciales inválidas")));
    }
}