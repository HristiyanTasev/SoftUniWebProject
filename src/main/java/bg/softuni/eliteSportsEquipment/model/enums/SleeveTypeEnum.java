package bg.softuni.eliteSportsEquipment.model.enums;

public enum SleeveTypeEnum {
    ELBOW("Elbows"),
    KNEE("Knees");

    private String sleeveType;

    SleeveTypeEnum(String sleeveType) {

        this.sleeveType = sleeveType;
    }

    @Override
    public String toString() {
        return sleeveType;
    }
}
