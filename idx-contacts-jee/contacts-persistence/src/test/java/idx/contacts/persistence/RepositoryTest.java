package idx.contacts.persistence;

import ch.frostnova.jee.testbase.TransactionalWrapper;
import idx.contacts.api.model.Gender;
import idx.contacts.persistence.entity.PersonEntity;
import idx.contacts.persistence.entity.PersonRepository;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RunWith(CdiTestRunner.class)
public class RepositoryTest {

    @Inject
    private PersonRepository repository;

    @Inject
    private TransactionalWrapper tx;

    @Test
    public void testCRUD() throws Exception {

        // create
        final PersonEntity person = new PersonEntity();
        person.setFirstName("Peter");
        person.setLastName("Walser");
        person.setGender(Gender.MALE);

        Assert.assertFalse(person.isPersistent());
        Assert.assertNull(person.getCreatedOn());
        Assert.assertNull(person.getLastUpdatedOn());

        final LocalDateTime beforeCreate = LocalDateTime.now();
        PersonEntity created = tx.execute(() -> repository.save(person));
        final LocalDateTime afterCreate = LocalDateTime.now();
        Assert.assertTrue(created.isPersistent());
        Assert.assertNotNull(created.getId());
        Assert.assertNotNull(created.getCreatedOn());
        Assert.assertNotNull(created.getLastUpdatedOn());
        Assert.assertFalse(created.getCreatedOn().isBefore(beforeCreate));
        Assert.assertFalse(created.getCreatedOn().isAfter(afterCreate));
        Assert.assertFalse(created.getLastUpdatedOn().isBefore(beforeCreate));
        Assert.assertFalse(created.getLastUpdatedOn().isAfter(afterCreate));
        long version = created.getVersion();

        // read
        PersonEntity read = tx.execute(() -> repository.findOne(person.getId()));
        Assert.assertEquals("Peter", read.getFirstName());
        Assert.assertEquals("Walser", read.getLastName());
        Assert.assertEquals(Gender.MALE, read.getGender());

        // update
        read.setDateOfBirth(LocalDate.of(1975, 12, 20));
        final LocalDateTime beforeUpdate = LocalDateTime.now();
        PersonEntity updated = tx.execute(() -> repository.save(person));
        final LocalDateTime afterUpdate = LocalDateTime.now();

        Assert.assertTrue(person.getVersion() > version);
        Assert.assertFalse(updated.getCreatedOn().isBefore(beforeCreate));
        Assert.assertFalse(updated.getCreatedOn().isAfter(afterCreate));
        Assert.assertFalse(updated.getLastUpdatedOn().isBefore(beforeUpdate));
        Assert.assertFalse(updated.getLastUpdatedOn().isAfter(afterUpdate));

        // delete
        repository.delete(updated.getId());
        PersonEntity deleted = repository.findOne(person.getId());
        Assert.assertNull(deleted);
    }
}
