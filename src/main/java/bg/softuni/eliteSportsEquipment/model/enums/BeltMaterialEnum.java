package bg.softuni.eliteSportsEquipment.model.enums;

public enum BeltMaterialEnum {
    LEATHER("Leather"),
    NYLON("Nylon");

    private String materialType;

    BeltMaterialEnum(String materialType) {

        this.materialType = materialType;
    }

    @Override
    public String toString() {
        return materialType;
    }
}
