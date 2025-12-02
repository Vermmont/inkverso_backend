package cl_javier_javiera.inkverso_backend.user;

public class UserUpdateDTO {
    private String password;
    private String role;
    private Boolean active;

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}