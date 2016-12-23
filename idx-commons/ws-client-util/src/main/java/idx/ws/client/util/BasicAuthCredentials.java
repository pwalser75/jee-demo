package idx.ws.client.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Basic Auth credentials (user and password) for web service authentication
 * Created by pwalser on 23.12.2016.
 */
public class BasicAuthCredentials {

    private final String basicAuth;

    /**
     * Constructor
     *
     * @param user     user
     * @param password password
     */
    public BasicAuthCredentials(String user, String password) {
        if (user == null) {
            basicAuth = "";
        } else {
            basicAuth = "Basic " + Base64.getEncoder()
                    .encodeToString((user + ":" + (password != null ? password : ""))
                            .getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * Returns the basic auth header value.
     *
     * @return basic auth
     */
    public String getBasicAuth() {
        return basicAuth;
    }
}
