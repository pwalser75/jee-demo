package ${basePackage}.client;

        import ${basePackage}.api.model.Gender;
        import ${basePackage}.api.model.Person;
        import idx.ws.client.util.BasicAuthCredentials;
        import idx.ws.client.util.ConnectionContext;
        import idx.ws.client.util.exception.UnauthorizedException;
        import org.junit.Assert;
        import org.junit.BeforeClass;
        import org.junit.Test;

        import javax.ws.rs.NotFoundException;
        import java.time.LocalDate;
        import java.util.List;

/**
 * Created by pwalser on 19.12.2016.
 */
public class ContactClientTest {

    private final static String BASE_URL = "https://localhost:8443/contacts";
    private final static BasicAuthCredentials basicAuth = new BasicAuthCredentials("testuser", "secret007");

    private static ContactClient contactClient;

    @BeforeClass
    public static void setup() throws Exception {
        ConnectionContext connectionContext = new ConnectionContext(BASE_URL, TestConnectionFactory.createClientBuilder(), basicAuth);
        contactClient = new ContactClient(connectionContext);
    }

    @Test(expected = UnauthorizedException.class)
    public void testAuthenticationRequired() throws Exception {

        ConnectionContext connectionContext = new ConnectionContext(BASE_URL, TestConnectionFactory.createClientBuilder(), null);
        ContactClient unauthorizedClient = new ContactClient(connectionContext);
        unauthorizedClient.list();
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongCredentials() throws Exception {

        BasicAuthCredentials wrongCredentials = new BasicAuthCredentials("wrong", "password");
        ConnectionContext connectionContext = new ConnectionContext(BASE_URL, TestConnectionFactory.createClientBuilder(), wrongCredentials);
        ContactClient unauthorizedClient = new ContactClient(connectionContext);
        unauthorizedClient.list();
    }

    @Test(expected = NotFoundException.class)
    public void testWrongURL() throws Exception {

        ConnectionContext connectionContext = new ConnectionContext(BASE_URL + "aaargh", TestConnectionFactory.createClientBuilder(), basicAuth);
        ContactClient unauthorizedClient = new ContactClient(connectionContext);
        unauthorizedClient.list();
    }

    @Test
    public void testList() throws Exception {

        List<Person> persons = contactClient.list();
        persons.forEach(p -> System.out.println(p + ": " + p.getFirstName() + " " + p.getLastName()));
    }

    @Test
    public void testCRUD() throws Exception {

        // create

        Person person = new Person();
        person.setFirstName("Terry");
        person.setLastName("Pratchett");
        person.setGender(Gender.MALE);
        person.setDateOfBirth(LocalDate.of(1948, 4, 28));

        Person created = contactClient.create(person);
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        Assert.assertEquals(person.getFirstName(), created.getFirstName());
        Assert.assertEquals(person.getLastName(), created.getLastName());
        Assert.assertEquals(person.getGender(), created.getGender());
        Assert.assertEquals(person.getDateOfBirth(), created.getDateOfBirth());
        long id = created.getId();
        person = created;

        // read

        Person loaded = contactClient.get(id);
        Assert.assertNotNull(loaded);
        Assert.assertNotNull(loaded.getId());
        Assert.assertEquals(person.getFirstName(), loaded.getFirstName());
        Assert.assertEquals(person.getLastName(), loaded.getLastName());
        Assert.assertEquals(person.getGender(), loaded.getGender());
        Assert.assertEquals(person.getDateOfBirth(), loaded.getDateOfBirth());

        // list

        Assert.assertTrue(contactClient.list().stream().anyMatch(p -> p.getId() == id));

        // update

        person.setFirstName("Douglas");
        person.setLastName("Adams");
        person.setGender(Gender.MALE);
        person.setDateOfBirth(LocalDate.of(1952, 3, 11));
        contactClient.save(person);

        loaded = contactClient.get(id);
        Assert.assertNotNull(loaded);
        Assert.assertEquals(person.getId(), loaded.getId());
        Assert.assertEquals(person.getFirstName(), loaded.getFirstName());
        Assert.assertEquals(person.getLastName(), loaded.getLastName());
        Assert.assertEquals(person.getGender(), loaded.getGender());
        Assert.assertEquals(person.getDateOfBirth(), loaded.getDateOfBirth());

        // delete

        contactClient.delete(id);

        // delete again - must not result in an exception
        contactClient.delete(id);

        // must not be found afterwards
        Assert.assertFalse(contactClient.list().stream().anyMatch(p -> p.getId() == id));

        try {
            contactClient.get(id);
            Assert.fail("Expected: NotFoundException");
        } catch (NotFoundException expected) {
            //
        }
    }
}
