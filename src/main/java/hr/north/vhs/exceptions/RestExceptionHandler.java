package hr.north.vhs.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PersonNotFoundException.class})
    protected ResponseEntity<Object> personNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Person not found",
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    @ExceptionHandler({VHSNotFoundException.class})
    protected ResponseEntity<Object> vhsNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "VHS not found",
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({RentalNotFoundException.class})
    protected ResponseEntity<Object> rentalNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Rental not found",
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({VHSAlreadyTakenException.class})
    protected ResponseEntity<Object> vhsAlreadyTaken(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "VHS already taken",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({UserNameAlreadyTakenException.class})
    protected ResponseEntity<Object> userNameAlreadyTaken(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Username already in use",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({CreationDateNullException.class})
    protected ResponseEntity<Object> creationDateNull(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Creation date must not be null",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ReturnDateNullException.class})
    protected ResponseEntity<Object> returnDateNull(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Return date must not be null",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({InvalidDateException.class})
    protected ResponseEntity<Object> invalidDate(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Return date must be after the creation date",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}