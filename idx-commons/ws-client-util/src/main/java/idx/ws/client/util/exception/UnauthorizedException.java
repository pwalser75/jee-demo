package idx.ws.client.util.exception;

/**
 * Created by pwalser on 23.12.2016.
 */
public class UnauthorizedException extends BaseHTTPException {

    public UnauthorizedException(String message) {
        super(401, message);
    }
}
