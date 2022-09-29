package hu.ponte.hr.exception;

public class DigitalSignFailedException extends RuntimeException {
    public DigitalSignFailedException() {
        super();
    }

    public DigitalSignFailedException(String message) {
        super(message);
    }
}
