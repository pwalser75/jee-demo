package idx.ws.client.util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Context for web service client: provides base url, the (preconfigured) client builder and basic auth credentials.
 * Created by pwalser on 22.12.2016.
 */
public class ConnectionContext {

    private final String baseURL;
    private final ClientBuilder clientBuilder;
    private final BasicAuthCredentials basicAuthCredentials;

    /**
     * Constructor
     *
     * @param baseURL              base URL (required)
     * @param clientBuilder        preconfigured client builder (required)
     * @param basicAuthCredentials user/password credentials for basic auth (optional)
     */
    public ConnectionContext(String baseURL, ClientBuilder clientBuilder, BasicAuthCredentials basicAuthCredentials) {
        this.baseURL = baseURL;
        this.clientBuilder = clientBuilder;
        this.basicAuthCredentials = basicAuthCredentials;
    }

    /**
     * Get the base URL
     *
     * @return base URL
     */
    public String getBaseURL() {
        return baseURL;
    }

    /**
     * Create a new client (using the client builder)
     *
     * @return client
     */
    public Client createClient() {
        return clientBuilder.build();
    }

    /**
     * Get the value for the 'Authentication' header.
     *
     * @return authentication
     */
    public String getAuth() {
        return basicAuthCredentials != null ? basicAuthCredentials.getBasicAuth() : "";
    }
}
