package idx.ws.client.util.exception;

import javax.xml.ws.http.HTTPException;

/**
 * Created by pwalser on 23.12.2016.
 */
public abstract class BaseHTTPException extends HTTPException {

    private final String message;

    protected BaseHTTPException(int status, String message) {
        super(status);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getLocalizedMessage() {
        return message;
    }

    @Override
    public String toString() {
        return getStatusCode() + (message != null ? ": " + message : "");
    }
}
