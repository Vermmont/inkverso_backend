package cl_javier_javiera.inkverso_backend.auth;

import cl_javier_javiera.inkverso_backend.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public LoginController(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
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