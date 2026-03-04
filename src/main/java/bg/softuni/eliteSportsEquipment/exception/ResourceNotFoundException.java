package bg.softuni.eliteSportsEquipment.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException forProduct(Long productId) {
        return new ResourceNotFoundException("Product with id " + productId + " not found");
    }

    public static ResourceNotFoundException forOrder(Long orderId) {
        return new ResourceNotFoundException("Order with id " + orderId + " not found");
    }

    public static ResourceNotFoundException forUser(String email) {
        return new ResourceNotFoundException("User with email " + email + " not found");
    }

    public static ResourceNotFoundException forUser(Long userId) {
        return new ResourceNotFoundException("User with id " + userId + " not found");
    }

    public static ResourceNotFoundException forCart(Long cartId) {
        return new ResourceNotFoundException("Cart with id " + cartId + " not found");
    }
}
