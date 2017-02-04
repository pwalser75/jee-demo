package idx.persistence.exception;

/**
 * Created by pwalser on 29.01.2017.
 */
public class DuplicateException extends RuntimeException {

    public DuplicateException(String message) {
        super(message);
    }
}
