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
 * The contact client provides the client API for the contact web service.
 * Created by pwalser on 19.12.2016.
 */
public class ContactClient {

    private final ConnectionContext connectionContext;

    /**
     * Constructor
     *
     * @param connectionContext connection context (required)
     */
    public ContactClient(ConnectionContext connectionContext) {
        if (connectionContext == null) {
            throw new IllegalArgumentException("Client builder is required");
        }
        this.connectionContext = connectionContext;
    }

    /**
     * List all persons
     *
     * @return list of persons (never null)
     */
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

    /**
     * Get a person by id. Throws a {@link javax.ws.rs.NotFoundException} if the person wasn't found.
     *
     * @param id id
     * @return person.
     */
    public Person get(long id) {
        Invocation invocation = connectionContext.createClient()
                .target(connectionContext.getBaseURL() + "/api/contact/" + id)
                .request()
                .header("Authorization", connectionContext.getAuth())
                .buildGet();

        Response response = ResponseExceptionMapper.check(invocation.invoke(), 200);
        return response.readEntity(Person.class);
    }

    /**
     * Create a new person with the provided data
     *
     * @param person data
     * @return created person
     */
    public Person create(Person person) {
        Invocation invocation = connectionContext.createClient()
                .target(connectionContext.getBaseURL() + "/api/contact")
                .request()
                .header("Authorization", connectionContext.getAuth())
                .buildPost(Entity.json(person));

        Response response = ResponseExceptionMapper.check(invocation.invoke(), 200);
        return response.readEntity(Person.class);
    }

    /**
     * Update a person
     *
     * @param person person (whose id is required)
     */
    public void save(Person person) {

        if (person.getId() == null) {
            throw new IllegalArgumentException("Person does not have an id, use the create() method instead");
        }

        Invocation invocation = connectionContext.createClient()
                .target(connectionContext.getBaseURL() + "/api/contact/" + person.getId())
                .request()
                .header("Authorization", connectionContext.getAuth())
                .buildPut(Entity.json(person));

        ResponseExceptionMapper.check(invocation.invoke(), 204);
    }

    /**
     * Delete the person with the given id, if it exists (no error thrown otherwise).
     *
     * @param id id of the record
     */
    public void delete(long id) {

        Invocation invocation = connectionContext.createClient()
                .target(connectionContext.getBaseURL() + "/api/contact/" + id)
                .request()
                .header("Authorization", connectionContext.getAuth())
                .buildDelete();

        ResponseExceptionMapper.check(invocation.invoke(), 204);
    }
}
