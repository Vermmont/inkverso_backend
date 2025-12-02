package cl_javier_javiera.inkverso_backend.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Productos", description = "CRUD de productos")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    @Operation(summary = "Listar productos")
    @GetMapping
    public List<ProductDTO> list() {
        return repo.findAll().stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Crear producto")
    @PostMapping
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody Product product) {
        Product saved = repo.save(product);
        return ResponseEntity.created(URI.create("/api/products/" + saved.getId()))
                             .body(new ProductDTO(saved));
    }

    @Operation(summary = "Obtener producto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> get(@PathVariable Long id) {
        return repo.findById(id)
                .map(p -> ResponseEntity.ok(new ProductDTO(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar producto")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody Product product) {
        return repo.findById(id).map(p -> {
            if (product.getName() != null) p.setName(product.getName());
            if (product.getPrice() != null) p.setPrice(product.getPrice());
            if (product.getDescription() != null) p.setDescription(product.getDescription());
            Product updated = repo.save(p);
            return ResponseEntity.ok(new ProductDTO(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar producto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
