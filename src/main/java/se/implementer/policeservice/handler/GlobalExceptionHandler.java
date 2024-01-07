package se.implementer.policeservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.implementer.policeservice.exception.KafkaPoliceException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private final String GLOBAL_ERROR_MESSAGE = "Global exception caught during service operation";

    @ExceptionHandler
    protected ResponseEntity<ErrorMessage> handleGlobalException(Throwable ex) {
        return createError(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    protected void handleKafkaPoliceException(KafkaPoliceException policeEx) {
        log.error(String.format(GLOBAL_ERROR_MESSAGE + " with message: %s and exception: %s", policeEx.getMessage(), policeEx));
    }

    private ResponseEntity<ErrorMessage> createError(Throwable ex, HttpStatus httpStatus) {
        log.error(String.format(GLOBAL_ERROR_MESSAGE + " with exception: %s", ex));
        return new ResponseEntity<>(
                new ErrorMessage(
                        GLOBAL_ERROR_MESSAGE,
                        ex.getMessage(),
                        ex
                ),
                httpStatus
        );
    }

    protected record ErrorMessage(String errorMessage, String exceptionMessage, Throwable e){}
}
