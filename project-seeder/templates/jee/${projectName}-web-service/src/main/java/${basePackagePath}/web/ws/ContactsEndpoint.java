package ${basePackage}.web.ws;

        import ${basePackage}.api.model.Person;
        import ${basePackage}.api.service.ContactService;

        import javax.enterprise.context.RequestScoped;
        import javax.inject.Inject;
        import javax.ws.rs.*;
        import javax.ws.rs.core.MediaType;
        import java.util.List;
        import java.util.NoSuchElementException;

/**
 * Full local path: https://localhost:8443/contacts/api/contacts
 *
 * @author pwalser
 */
@RequestScoped
@Path("contact")
public class ContactsEndpoint {

    @Inject
    private ContactService contactService;

    /**
     * List contacts
     *
     * @return list of contacts (never null)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> list() {
        return contactService.list();
    }

    /**
     * Get a record by id. If the contact was not found, a NoSuchElementException will be thrown (resulting in a 404 NOT FOUND).
     *
     * @param id id of the record
     * @return record
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person get(@PathParam("id") long id) {
        Person result = contactService.get(id);
        if (result != null) {
            return result;
        }
        throw new NoSuchElementException();
    }

    /**
     * Create a new record (or updates an existing contact, when the id is set).
     *
     * @param person record to create
     * @return created record
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Person save(Person person) {
        return contactService.save(person);
    }

    /**
     * Update a record
     *
     * @param id     id of the record to update
     * @param person new data to set
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("id") long id, Person person) {
        person.setId(id);
        contactService.save(person);
    }

    /**
     * Delete a record
     *
     * @param id id of the record
     */
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") long id) {
        contactService.delete(id);
    }
}
