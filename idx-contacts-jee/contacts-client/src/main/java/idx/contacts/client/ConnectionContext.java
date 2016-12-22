package idx.contacts.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Created by pwalser on 22.12.2016.
 */
public class ConnectionContext {

    private final String baseURL;
    private final ClientBuilder clientBuilder;
    private final String basicAuth;

    public ConnectionContext(String baseURL, ClientBuilder clientBuilder, String user, String password) {
        this.baseURL = baseURL;
        this.clientBuilder = clientBuilder;

        basicAuth = "Basic " + Base64.getEncoder().encodeToString((user + ":" + password).getBytes(StandardCharsets.UTF_8));
    }

    public String getBaseURL() {
        return baseURL;
    }

    public Client createClient() {
        return clientBuilder.build();
    }

    public String getAuthHeader() {
        return basicAuth;
    }

}
