public class NotFoundException extends RuntimeException {
    public NotFoundException(String parameter, String message) {
        super(message);
    }
}
