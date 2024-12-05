package dh.scheduler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class passwordMismatchException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;

    public passwordMismatchException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
