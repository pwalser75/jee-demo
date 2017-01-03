package ${basePackage}.persistence;

import ${basePackage}.persistence.entity.NoteEntity;
import idx.persistence.repository.Repository;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@RunWith(CdiTestRunner.class)
public class RepositoryTest {

    @Inject
    private Repository<NoteEntity, Long> repository;

    @Test
    public void testCRUD() {

        // create
        NoteEntity note = new NoteEntity();
        note.setText("Aloha");

        Assert.assertFalse(note.isPersistent());
        note = repository.save(note);
        Assert.assertTrue(note.isPersistent());
        Assert.assertNotNull(note.getId());

        // read
        note = repository.get(note.getId());
        Assert.assertEquals("Aloha", note.getText());

        // update
        note.setText("Lorem ipsum dolor sit amet");
        note = repository.save(note);

        // delete
        repository.delete(note.getId());
        note = repository.get(note.getId());
        Assert.assertNull(note);
    }
}
