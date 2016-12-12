package idx.contacts.persistence;

import idx.contacts.api.model.Gender;
import idx.contacts.persistence.entity.PersonEntity;
import idx.persistence.repository.Repository;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.time.LocalDate;

@RunWith(CdiTestRunner.class)
public class RepositoryTest {

    @Inject
    private Repository<PersonEntity, Long> repository;

    @Test
    public void testCRUD() {

        // create
        PersonEntity person = new PersonEntity();
        person.setFirstName("Peter");
        person.setLastName("Walser");
        person.setGender(Gender.MALE);

        Assert.assertFalse(person.isPersistent());
        person = repository.save(person);
        Assert.assertTrue(person.isPersistent());
        Assert.assertNotNull(person.getId());

        // read
        person = repository.get(person.getId());
        Assert.assertEquals("Peter", person.getFirstName());
        Assert.assertEquals("Walser", person.getLastName());
        Assert.assertEquals(Gender.MALE, person.getGender());

        // update
        person.setDateOfBirth(LocalDate.of(1975, 12, 20));
        person = repository.save(person);

        // delete
        repository.delete(person.getId());
        person = repository.get(person.getId());
        Assert.assertNull(person);
    }
}
