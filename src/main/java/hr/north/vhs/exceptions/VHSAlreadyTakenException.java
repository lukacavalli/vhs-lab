package hr.north.vhs.exceptions;

public class VHSAlreadyTakenException extends RuntimeException {
    public VHSAlreadyTakenException(Long id) {
        super("VHS id already taken: " + id);
    }
}
