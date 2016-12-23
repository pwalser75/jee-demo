package idx.contacts.client;

import idx.contacts.api.model.Person;
import idx.ws.client.util.ConnectionContext;
import idx.ws.client.util.ResponseExceptionMapper;

import javax.ws.rs.client.Entity;
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
                .header("Authorization", connectionContext.getAuth())
                .buildGet();

        Response response = ResponseExceptionMapper.check(invocation.invoke(), 200);
        return response.readEntity(new GenericType<List<Person>>() {
        });
    }

    public Person get(long id) {
        Invocation invocation = connectionContext.createClient()
                .target(connectionContext.getBaseURL() + "/api/contact/" + id)
                .request()
                .header("Authorization", connectionContext.getAuth())
                .buildGet();

        Response response = ResponseExceptionMapper.check(invocation.invoke(), 200);
        return response.readEntity(Person.class);
    }

    public Person create(Person person) {
        Invocation invocation = connectionContext.createClient()
                .target(connectionContext.getBaseURL() + "/api/contact")
                .request()
                .header("Authorization", connectionContext.getAuth())
                .buildPost(Entity.json(person));

        Response response = ResponseExceptionMapper.check(invocation.invoke(), 200);
        return response.readEntity(Person.class);
    }

    public void save(Person person) {

        if (person.getId() == null) {
            throw new IllegalArgumentException("Person does not have an it, use the create() method instead");
        }

        Invocation invocation = connectionContext.createClient()
                .target(connectionContext.getBaseURL() + "/api/contact/" + person.getId())
                .request()
                .header("Authorization", connectionContext.getAuth())
                .buildPut(Entity.json(person));

        ResponseExceptionMapper.check(invocation.invoke(), 204);
    }

    public void delete(long id) {

        Invocation invocation = connectionContext.createClient()
                .target(connectionContext.getBaseURL() + "/api/contact/" + id)
                .request()
                .header("Authorization", connectionContext.getAuth())
                .buildDelete();

        ResponseExceptionMapper.check(invocation.invoke(), 204);
    }
}
