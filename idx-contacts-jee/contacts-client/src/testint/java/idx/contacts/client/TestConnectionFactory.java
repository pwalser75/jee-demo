package idx.contacts.client;

import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.ClientBuilder;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * Created by pwalser on 22.12.2016.
 */
public final class TestConnectionFactory {

    private TestConnectionFactory() {

    }

    public static ClientBuilder createClientBuilder() throws Exception {

        try (InputStream in = ContactClientTest.class.getResourceAsStream("/client-truststore.jks")) {
            KeyStore truststore = KeyStore.getInstance(KeyStore.getDefaultType());
            truststore.load(in, "truststore".toCharArray());
            return ClientBuilder.newBuilder()
                    .trustStore(truststore)
                    .property(ClientProperties.CONNECT_TIMEOUT, 500)
                    .property(ClientProperties.READ_TIMEOUT, 5000)
                    .hostnameVerifier((hostname, sslSession) -> "localhost".equals(hostname));
        }
    }
}
