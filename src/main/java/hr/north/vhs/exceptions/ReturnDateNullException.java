package hr.north.vhs.exceptions;

public class ReturnDateNullException extends RuntimeException {
    public ReturnDateNullException() {
        super("Return date must not be null");
    }
}
