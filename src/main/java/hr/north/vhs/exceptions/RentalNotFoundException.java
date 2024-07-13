package hr.north.vhs.exceptions;

public class RentalNotFoundException extends RuntimeException {
    public RentalNotFoundException(Long id) {
        super("Rental id not found: " + id);
    }
}
