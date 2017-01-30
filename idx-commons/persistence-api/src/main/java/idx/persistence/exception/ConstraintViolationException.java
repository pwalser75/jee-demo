package idx.persistence.exception;

/**
 * Created by pwalser on 29.01.2017.
 */
public class ConstraintViolationException extends RuntimeException {

    public ConstraintViolationException(String message) {
        super(message);
    }
}
