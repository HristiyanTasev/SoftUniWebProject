package bg.softuni.eliteSportsEquipment.exception;

public class TokenValidationException extends RuntimeException {
    private final String redirectPath;
    private final String flashKey;
    private final String flashValue;

    public TokenValidationException(String message, String redirectPath, String flashKey, String flashValue) {
        super(message);
        this.redirectPath = redirectPath;
        this.flashKey = flashKey;
        this.flashValue = flashValue;
    }

    public String getRedirectPath() { return redirectPath; }
    public String getFlashKey() { return flashKey; }
    public String getFlashValue() { return flashValue; }
}
