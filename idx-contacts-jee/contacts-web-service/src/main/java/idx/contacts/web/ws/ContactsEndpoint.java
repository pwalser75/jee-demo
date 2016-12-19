package idx.contacts.web.ws;

import idx.contacts.api.model.Gender;
import idx.contacts.api.model.Person;
import idx.contacts.api.service.ContactService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
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
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> list() {
        return contactService.list();
    }

    /**
     * Get a contact
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
     * Create a new contact (or updates an existing contact, when the id is set).
     *
     * @param person
     * @return created contact
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Person save(Person person) {
        return contactService.save(person);
    }

    /**
     * Update contact
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
     * Delete a contact
     *
     * @param id person id
     */
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") long id) {
        contactService.delete(id);
    }

    /**
     * Get a contact
     */
    @GET
    @Path("/testdata")
    public void createTestData() {
        for (Person person : contactService.list()) {
            contactService.delete(person.getId());
        }

        contactService.save(new Person("Peter", "Walser", Gender.MALE, LocalDate.of(1975, 10, 20)));
        contactService.save(new Person("Manuela", "Walser", Gender.FEMALE, LocalDate.of(1979, 10, 24)));
        contactService.save(new Person("Nathan", "Walser", Gender.MALE, LocalDate.of(2006, 2, 8)));
        contactService.save(new Person("Colin", "Walser", Gender.MALE, LocalDate.of(2008, 6, 11)));
    }
}
