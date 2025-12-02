package cl_javier_javiera.inkverso_backend.product;

public class ProductUpdateDTO {
    private String name;
    private Double price;
    private Boolean active;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}