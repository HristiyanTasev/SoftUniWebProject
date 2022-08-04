package bg.softuni.eliteSportsEquipment.model.enums;

public enum StrapTypeEnum {
    CLOSED_LOOP("Closed Loop"),
    LASSO("Lasso"),
    FIGURE_EIGHT("Figure Eight");

    private String strapType;

    StrapTypeEnum(String strapType) {

        this.strapType = strapType;
    }

    @Override
    public String toString() {
        return strapType;
    }
}
