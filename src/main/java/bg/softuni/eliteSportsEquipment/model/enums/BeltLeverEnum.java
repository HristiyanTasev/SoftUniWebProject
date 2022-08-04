package bg.softuni.eliteSportsEquipment.model.enums;

public enum BeltLeverEnum {
    PRONG("Prong"),
    LEVER("Lever");

    private String leverType;

    BeltLeverEnum(String leverType) {

        this.leverType = leverType;
    }

    @Override
    public String toString() {
        return leverType;
    }
}
