package hr.north.vhs.exceptions;

public class UserNameAlreadyTakenException extends RuntimeException {

    public UserNameAlreadyTakenException() {
        super("Username already in use");
    }

}
