package bg.softuni.eliteSportsEquipment.model.enums;

public enum UserRoleEnum {
    ADMIN("Admin"),
    MODERATOR("Moderator");

    private String role;

    UserRoleEnum(String role) {

        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
