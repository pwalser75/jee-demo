package ${basePackage}.service;

import ${basePackage}.api.model.Note;
import ${basePackage}.api.service.NoteService;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RunWith(CdiTestRunner.class)
public class NoteServiceTest {

    @Inject
    private NoteService noteService;

    @Test
    public void testCRUD() {
        Assert.assertNotNull(noteService);

        Note note = new Note();
        note.setText("Aloha");

        // create
        Assert.assertNull(note.getId());
        Note persisted = noteService.save(note);
        Assert.assertNotNull(persisted.getId());

        // read: get
        Note loaded = noteService.get(persisted.getId());
        Assert.assertNotNull(loaded);
        Assert.assertEquals(note.getText(), loaded.getText());

        // read: list
        List<Note> list = noteService.list();
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
        Map<Long, Note> listById = list.stream().collect(Collectors.toMap(Note::getId, Function.identity()));
        Note listed = listById.get(persisted.getId());

        Assert.assertNotNull(listed);
        Assert.assertEquals(note.getText(), listed.getText());

        // update
        listed.setText("Lorem ipsum dolor sit amet");
        noteService.save(listed);
        loaded = noteService.get(persisted.getId());
        Assert.assertNotNull(loaded);
        Assert.assertEquals(listed.getText(), loaded.getText());

        // delete
        noteService.delete(loaded.getId());
        loaded = noteService.get(persisted.getId());
        Assert.assertNull(loaded);
    }

    @Test
    public void testInitialDataFound() {

        boolean welcomeFound = false;
        boolean bonjourFound = false;

        for (Note note : noteService.list()) {
            welcomeFound = welcomeFound || note.getText().equals("Welcome");
            bonjourFound = bonjourFound || note.getText().equals("Bonjour");
        }
        Assert.assertTrue(welcomeFound);
        Assert.assertTrue(bonjourFound);
    }
}
