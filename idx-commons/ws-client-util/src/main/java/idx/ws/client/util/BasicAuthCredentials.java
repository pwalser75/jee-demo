package idx.ws.client.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Created by pwalser on 23.12.2016.
 */
public class BasicAuthCredentials {

    private String basicAuth;

    public BasicAuthCredentials(String user, String password) {
        basicAuth = "Basic " + Base64.getEncoder().encodeToString((user + ":" + password).getBytes(StandardCharsets.UTF_8));
    }

    public String getBasicAuth() {
        return basicAuth;
    }
}
