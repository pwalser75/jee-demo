package idx.contacts.client;

import idx.contacts.api.model.Person;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by pwalser on 19.12.2016.
 */
public class ContactClient {

    private final ConnectionContext connectionContext;

    public ContactClient(ConnectionContext connectionContext) {
        if (connectionContext == null) {
            throw new IllegalArgumentException("Client builder is required");
        }
        this.connectionContext = connectionContext;
    }

    public List<Person> list() {
        Invocation invocation = connectionContext.createClient()
                .target(connectionContext.getBaseURL() + "/api/contact")
                .request()
                .header("Authorization", connectionContext.getAuthHeader())
                .buildGet();


        Response response = invocation.invoke();
        if (response.getStatus() == 200) {
            if (response.bufferEntity()) {
                System.out.println(response.readEntity(String.class));
            }

            List<Person> result = response.readEntity(new GenericType<List<Person>>() {
            });
            return result;
        }
        throw new IllegalStateException("Unexpected return status: " + response.getStatus());
    }
}
