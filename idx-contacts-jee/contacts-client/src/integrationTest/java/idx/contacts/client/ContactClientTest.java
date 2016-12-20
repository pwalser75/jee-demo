package idx.contacts.client;

import org.glassfish.jersey.client.ClientProperties;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.Base64;

/**
 * Created by pwalser on 19.12.2016.
 */
public class ContactClientTest {


    private final String BASE_URL = "https://localhost:8443/contacts";

    private static ClientBuilder clientBuilder;


    @BeforeClass
    public static void setup() throws Exception {

        try (InputStream in = ContactClientTest.class.getResourceAsStream("/client-truststore.jks")) {
            KeyStore truststore = KeyStore.getInstance(KeyStore.getDefaultType());
            truststore.load(in, "truststore".toCharArray());
            clientBuilder = ClientBuilder.newBuilder()
                    .trustStore(truststore)
                    .property(ClientProperties.CONNECT_TIMEOUT, 500)
                    .property(ClientProperties.READ_TIMEOUT, 5000)
                    .hostnameVerifier((hostname, sslSession) -> "localhost".equals(hostname));
        }

    }

    @Test
    public void testAuthenticationRequired() {
        Client client = clientBuilder.build();
        Invocation invocation = client
                .target(BASE_URL + "/api/contact")
                .request()
                .buildGet();

        Response response = invocation.invoke();
        Assert.assertEquals(401, response.getStatus());
    }

    @Test
    public void testList() {
        Client client = clientBuilder.build();

        String basicAuth = "Basic " + Base64.getEncoder().encodeToString("testuser:secret007".getBytes(StandardCharsets.UTF_8));
        System.out.println("Credentials: " + basicAuth);
        Invocation invocation = client
                .target(BASE_URL + "/api/contact")
                .request()
                .header("Authorization", basicAuth)
                .buildGet();

        Response response = invocation.invoke();
        Assert.assertEquals(200, response.getStatus());
        System.out.println(response.readEntity(String.class));
    }
}
