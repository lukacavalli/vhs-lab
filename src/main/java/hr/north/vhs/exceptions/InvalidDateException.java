package hr.north.vhs.exceptions;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException() {
        super("Date is not valid");
    }
}
