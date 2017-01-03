package ${basePackage}.web.ws;

import ${basePackage}.api.model.Note;
import ${basePackage}.api.service.NoteService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Full local path: https://localhost:8443/notes/api/notes
 *
 * @author pwalser
 */
@RequestScoped
@Path("notes")
public class NotesEndpoint {

    @Inject
    private NoteService noteService;

    /**
     * List notes
     *
     * @return list of notes (never null)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Note> list() {
        return noteService.list();
    }

    /**
     * Get a record by id. If the record was not found, a NoSuchElementException will be thrown (resulting in a 404 NOT FOUND).
     *
     * @param id id of the record
     * @return record
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Note get(@PathParam("id") long id) {
        Note result = noteService.get(id);
        if (result != null) {
            return result;
        }
        throw new NoSuchElementException();
    }

    /**
     * Create a new record (or updates an existing record, when the id is set).
     *
     * @param note record to create
     * @return created record
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Note save(Note note) {
        return noteService.save(note);
    }

    /**
     * Update a record
     *
     * @param id   id of the record to update
     * @param note new data to set
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("id") long id, Note note) {
        note.setId(id);
        noteService.save(note);
    }

    /**
     * Delete a record
     *
     * @param id id of the record
     */
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") long id) {
        noteService.delete(id);
    }
}
