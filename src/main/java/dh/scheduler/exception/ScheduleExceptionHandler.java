package dh.scheduler.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ScheduleExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException() {
        return ResponseEntity.status(404).body("id를 찾을 수 없습니다.");
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<String> errorMessages = e.getBindingResult().getFieldErrors().stream().map(message -> message.getDefaultMessage()).toList();

        return ResponseEntity.status(400).body(errorMessages);
    }

    @ExceptionHandler(passwordMismatchException.class)
    public ResponseEntity<String> handlepasswordMismatchException(passwordMismatchException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

}
