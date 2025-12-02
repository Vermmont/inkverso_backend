package cl_javier_javiera.inkverso_backend.user;

public class UserDTO {
    private Long id;
    private String email;
    private String role;
    private Boolean active;

    public UserDTO(User u) {
        this.id = u.getId();
        this.email = u.getEmail();
        this.role = u.getRole();
        this.active = u.getActive(); 
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}