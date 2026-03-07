package ma.ismagi.inventoryservice.exception;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import ma.ismagi.inventoryservice.exception.model.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** GlobalExceptionHandler */
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorDetails> handleEntityNotFoundException(
      EntityNotFoundException exception) {
    return new ResponseEntity<>(
        new ErrorDetails(LocalDateTime.now(), exception.getMessage(), HttpStatus.NOT_FOUND.value()),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDetails> handleException(Exception exception) {
    return new ResponseEntity<>(
        new ErrorDetails(
            LocalDateTime.now(), exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorDetails> handleRuntimeException(RuntimeException exception) {
    return new ResponseEntity<>(
        new ErrorDetails(
            LocalDateTime.now(), exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ErrorDetails> handleIllegalStateException(IllegalStateException exception) {
    return new ResponseEntity<>(
        new ErrorDetails(
            LocalDateTime.now(), exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
