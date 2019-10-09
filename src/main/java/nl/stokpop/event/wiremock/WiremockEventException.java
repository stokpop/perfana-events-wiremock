package nl.stokpop.event.wiremock;

public class WiremockEventException extends RuntimeException {
    public WiremockEventException(String message) {
        super(message);
    }

    public WiremockEventException(String message, Throwable cause) {
        super(message, cause);
    }
}
