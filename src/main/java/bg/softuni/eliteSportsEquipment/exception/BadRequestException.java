package bg.softuni.eliteSportsEquipment.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public static BadRequestException emptyCart() {
        return new BadRequestException("Cannot create order from empty cart");
    }
}