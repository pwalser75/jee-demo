package idx.ws.client.util.exception;

/**
 * Created by pwalser on 23.12.2016.
 */
public class GenericServerExceptinon extends BaseHTTPException {

    public GenericServerExceptinon(int status, String message) {
        super(status, message);
    }
}
