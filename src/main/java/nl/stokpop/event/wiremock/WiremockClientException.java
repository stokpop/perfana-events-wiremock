package nl.stokpop.event.wiremock;

public class WiremockClientException extends RuntimeException {
    public WiremockClientException(String message) {
        super(message);
    }

    public WiremockClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
