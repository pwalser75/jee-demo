package ${basePackage}.client;

import ${basePackage}.api.model.Note;
import idx.ws.client.util.ConnectionContext;
import idx.ws.client.util.ResponseExceptionMapper;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * The client provides the client API for the web service.
 */
public class NotesClient {

    private final ConnectionContext connectionContext;
    private final static String BASE_PATH = "/api/notes";

    /**
     * Constructor
     *
     * @param connectionContext connection context (required)
     */
    public NotesClient(ConnectionContext connectionContext) {
        if (connectionContext == null) {
            throw new IllegalArgumentException("Client builder is required");
        }
        this.connectionContext = connectionContext;
    }

    /**
     * List all notes
     *
     * @return list of notes (never null)
     */
    public List<Note> list() {
        Invocation invocation = connectionContext.createClient()
                .target(connectionContext.getBaseURL() + BASE_PATH)
                .request()
                .header("Authorization", connectionContext.getAuth())
                .buildGet();

        Response response = ResponseExceptionMapper.check(invocation.invoke(), 200);
        return response.readEntity(new GenericType<List<Note>>() {
        });
    }

    /**
     * Get a note by id. Throws a {@link javax.ws.rs.NotFoundException} if the note wasn't found.
     *
     * @param id id
     * @return note.
     */
    public Note get(long id) {
        Invocation invocation = connectionContext.createClient()
                .target(connectionContext.getBaseURL() + BASE_PATH + id)
                .request()
                .header("Authorization", connectionContext.getAuth())
                .buildGet();

        Response response = ResponseExceptionMapper.check(invocation.invoke(), 200);
        return response.readEntity(Note.class);
    }

    /**
     * Create a new note with the provided data
     *
     * @param note data
     * @return created note
     */
    public Note create(Note note) {
        Invocation invocation = connectionContext.createClient()
                .target(connectionContext.getBaseURL() + BASE_PATH)
                .request()
                .header("Authorization", connectionContext.getAuth())
                .buildPost(Entity.json(note));

        Response response = ResponseExceptionMapper.check(invocation.invoke(), 200);
        return response.readEntity(Note.class);
    }

    /**
     * Update a note
     *
     * @param note note (whose id is required)
     */
    public void save(Note note) {

        if (note.getId() == null) {
            throw new IllegalArgumentException("Not yet persisted, use the create() method instead");
        }

        Invocation invocation = connectionContext.createClient()
                .target(connectionContext.getBaseURL() + BASE_PATH + "/" + note.getId())
                .request()
                .header("Authorization", connectionContext.getAuth())
                .buildPut(Entity.json(note));

        ResponseExceptionMapper.check(invocation.invoke(), 204);
    }

    /**
     * Delete the note with the given id, if it exists (no error thrown otherwise).
     *
     * @param id id of the record
     */
    public void delete(long id) {

        Invocation invocation = connectionContext.createClient()
                .target(connectionContext.getBaseURL() + BASE_PATH + "/" + id)
                .request()
                .header("Authorization", connectionContext.getAuth())
                .buildDelete();

        ResponseExceptionMapper.check(invocation.invoke(), 204);
    }
}
