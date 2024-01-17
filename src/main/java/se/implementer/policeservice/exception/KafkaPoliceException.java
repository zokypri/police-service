package se.implementer.policeservice.exception;

public class KafkaPoliceException extends RuntimeException {

    private final static String errorMessage = "Unable to publish kafka police warning event: %s";

    public KafkaPoliceException(Throwable ex) {
        super(String.format(errorMessage, ex.getMessage()), ex);
    }
}
