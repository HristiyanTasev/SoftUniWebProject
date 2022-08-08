package bg.softuni.eliteSportsEquipment.model.dto;

public class SearchDTO {

    private String type;
    private String brand;
    private String name;

    public SearchDTO() {
    }

    public String getType() {
        return type;
    }

    public SearchDTO setType(String type) {
        this.type = type;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public SearchDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getName() {
        return name;
    }

    public SearchDTO setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isEmpty() {
        return (this.brand == null || this.brand.isEmpty()) &&
                (this.name == null || this.name.isEmpty()) &&
                (this.type == null || this.type.isEmpty());
    }
}
