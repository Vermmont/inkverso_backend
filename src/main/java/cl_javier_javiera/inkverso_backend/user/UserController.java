package cl_javier_javiera.inkverso_backend.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "Usuarios", description = "CRUD de usuarios")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "Listar usuarios")
    @GetMapping
    public List<UserDTO> list() {
        return repo.findAll().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Crear usuario")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserCreateDTO dto) {
        if (repo.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email ya registrado"));
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        User u = new User(null, dto.getEmail(), encodedPassword, "USER", true);
        User saved = repo.save(u);

        return ResponseEntity.created(URI.create("/api/users/" + saved.getId()))
                             .body(new UserDTO(saved));
    }

    @Operation(summary = "Obtener usuario por ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable Long id) {
        return repo.findById(id)
                .map(u -> ResponseEntity.ok(new UserDTO(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar usuario")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserUpdateDTO dto) {
        return repo.findById(id).map(u -> {
            if (dto.getPassword() != null) u.setPassword(passwordEncoder.encode(dto.getPassword()));
            if (dto.getRole() != null) u.setRole(dto.getRole());
            if (dto.getActive() != null) u.setActive(dto.getActive());
            User updated = repo.save(u);
            return ResponseEntity.ok(new UserDTO(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar usuario")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}