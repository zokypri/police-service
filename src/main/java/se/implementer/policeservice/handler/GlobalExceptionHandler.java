package se.implementer.policeservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.implementer.policeservice.exception.KafkaPoliceException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private final String GLOBAL_ERROR_MESSAGE = "Exception caught: %s";

    @ExceptionHandler
    protected ResponseEntity<ErrorMessage> handleGlobalException(MethodArgumentNotValidException ex) {
        return createError(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorMessage> handleGlobalException(Throwable ex) {
        return createError(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorMessage> handleKafkaPoliceException(KafkaPoliceException policeEx) {
        return createError(policeEx, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorMessage> createError(Throwable ex, HttpStatus httpStatus) {
        String errorMessage = String.format(GLOBAL_ERROR_MESSAGE, ex.getMessage());
        log.error(String.format(errorMessage + " with exception: %s", ex.getMessage(), ex));
        return new ResponseEntity<>(
                new ErrorMessage(errorMessage),
                httpStatus
        );
    }

    protected record ErrorMessage(String exceptionMessage){}
}
