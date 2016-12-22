package idx.contacts.client;

import idx.contacts.api.model.Person;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by pwalser on 19.12.2016.
 */
public class ContactClientTest {

    private final String BASE_URL = "https://localhost:8443/contacts";

    @Test
    public void testAuthenticationRequired() throws Exception {

        Invocation invocation = TestConnectionFactory.createClientBuilder().build()
                .target(BASE_URL + "/api/contact")
                .request()
                .buildGet();

        Response response = invocation.invoke();
        Assert.assertEquals(401, response.getStatus());
    }


    @Test
    public void testList() throws Exception {

        ConnectionContext connectionContext = new ConnectionContext(BASE_URL, TestConnectionFactory.createClientBuilder(), "testuser", "secret007");
        ContactClient contactClient = new ContactClient(connectionContext);

        List<Person> persons = contactClient.list();
        persons.forEach(p -> System.out.println(p + ": " + p.getFirstName() + " " + p.getLastName()));
    }

}
