package idx.ws.client.util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by pwalser on 22.12.2016.
 */
public class ConnectionContext {

    private final String baseURL;
    private final ClientBuilder clientBuilder;
    private final BasicAuthCredentials basicAuthCredentials;

    public ConnectionContext(String baseURL, ClientBuilder clientBuilder, BasicAuthCredentials basicAuthCredentials) {
        this.baseURL = baseURL;
        this.clientBuilder = clientBuilder;
        this.basicAuthCredentials = basicAuthCredentials;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public Client createClient() {
        return clientBuilder.build();
    }

    public String getAuth() {
        return basicAuthCredentials != null ? basicAuthCredentials.getBasicAuth() : null;
    }

    public BasicAuthCredentials getBasicAuthCredentials() {
        return basicAuthCredentials;
    }
}
