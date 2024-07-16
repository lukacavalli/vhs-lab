package hr.north.vhs.exceptions;

public class CreationDateNullException extends RuntimeException {
    public CreationDateNullException() {
        super("Creation date must not be null");
    }
}
