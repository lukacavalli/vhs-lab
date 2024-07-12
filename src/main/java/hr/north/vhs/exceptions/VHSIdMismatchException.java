package hr.north.vhs.exceptions;

public class VHSIdMismatchException extends RuntimeException{
    public VHSIdMismatchException(Long id) {
        super("VHS id mismatch: " + id);
    }
}
