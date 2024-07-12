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
}