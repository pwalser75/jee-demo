package ${basePackage}.client;

import ${basePackage}.api.model.Note;
import idx.ws.client.util.BasicAuthCredentials;
import idx.ws.client.util.ConnectionContext;
import idx.ws.client.util.exception.UnauthorizedException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.NotFoundException;
import java.util.List;

/**
 * Created by pwalser on 19.12.2016.
 */
public class NotesClientTest {

    private final static String BASE_URL = "https://localhost:8443/notes";
    private final static BasicAuthCredentials basicAuth = new BasicAuthCredentials("testuser", "secret007");

    private static NotesClient notesClient;

    @BeforeClass
    public static void setup() throws Exception {
        ConnectionContext connectionContext = new ConnectionContext(BASE_URL, TestConnectionFactory.createClientBuilder(), basicAuth);
        notesClient = new NotesClient(connectionContext);
    }

    @Test(expected = UnauthorizedException.class)
    public void testAuthenticationRequired() throws Exception {

        ConnectionContext connectionContext = new ConnectionContext(BASE_URL, TestConnectionFactory.createClientBuilder(), null);
        NotesClient unauthorizedClient = new NotesClient(connectionContext);
        unauthorizedClient.list();
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongCredentials() throws Exception {

        BasicAuthCredentials wrongCredentials = new BasicAuthCredentials("wrong", "password");
        ConnectionContext connectionContext = new ConnectionContext(BASE_URL, TestConnectionFactory.createClientBuilder(), wrongCredentials);
        NotesClient unauthorizedClient = new NotesClient(connectionContext);
        unauthorizedClient.list();
    }

    @Test(expected = NotFoundException.class)
    public void testWrongURL() throws Exception {

        ConnectionContext connectionContext = new ConnectionContext(BASE_URL + "aaargh", TestConnectionFactory.createClientBuilder(), basicAuth);
        NotesClient unauthorizedClient = new NotesClient(connectionContext);
        unauthorizedClient.list();
    }

    @Test
    public void testList() throws Exception {

        List<Note> notes = notesClient.list();
        notes.forEach(p -> System.out.println(p + ": " + p.getText()));
    }

    @Test
    public void testCRUD() throws Exception {

        // create

        Note note = new Note();
        note.setText("Aloha");

        Note created = notesClient.create(note);
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        Assert.assertEquals(note.getText(), created.getText());
        long id = created.getId();
        note = created;

        // read

        Note loaded = notesClient.get(id);
        Assert.assertNotNull(loaded);
        Assert.assertNotNull(loaded.getId());
        Assert.assertEquals(note.getText(), loaded.getText());

        // list

        Assert.assertTrue(notesClient.list().stream().anyMatch(p -> p.getId() == id));

        // update

        note.setText("Lorem ipsum dolor sit amet");
        notesClient.save(note);

        loaded = notesClient.get(id);
        Assert.assertNotNull(loaded);
        Assert.assertEquals(note.getId(), loaded.getId());
        Assert.assertEquals(note.getText(), loaded.getText());

        // delete

        notesClient.delete(id);

        // delete again - must not result in an exception
        notesClient.delete(id);

        // must not be found afterwards
        Assert.assertFalse(notesClient.list().stream().anyMatch(p -> p.getId() == id));

        try {
            notesClient.get(id);
            Assert.fail("Expected: NotFoundException");
        } catch (NotFoundException expected) {
            //
        }
    }
}
