package hr.north.vhs.exceptions;

public class VHSNotFoundException extends RuntimeException {
    public VHSNotFoundException(Long id) {
        super("VHS id not found: " + id);
    }
}
