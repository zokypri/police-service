package se.implementer.policeservice.exception;

public class KafkaPoliceException extends Exception {
    public KafkaPoliceException(String errorMessage, Throwable ex) {
        super(errorMessage, ex);
    }
}
