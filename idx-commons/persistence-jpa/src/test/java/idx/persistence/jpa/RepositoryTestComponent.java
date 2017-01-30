package idx.persistence.jpa;

import idx.persistence.jpa.example.Gender;
import idx.persistence.jpa.example.Person;
import idx.persistence.repository.Repository;
import org.junit.Assert;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;

@Dependent
public class RepositoryTestComponent {

    @Inject
    private EntityManager entityManager;

    @Inject
    private Repository<Person, Long> repository;

    @Transactional
    public void testCRUD() {

        // create
        Person person = new Person();
        person.setFirstName("Peter");
        person.setLastName("Walser");
        person.setEmailAddress("user@test.org");
        person.setGender(Gender.MALE);

        Assert.assertFalse(person.isPersistent());
        person = repository.save(person);
        Assert.assertTrue(person.isPersistent());
        Assert.assertNotNull(person.getId());

        // read
        person = repository.get(person.getId());
        Assert.assertEquals("Peter", person.getFirstName());
        Assert.assertEquals("Walser", person.getLastName());
        Assert.assertEquals("user@test.org", person.getEmailAddress());
        Assert.assertEquals(Gender.MALE, person.getGender());

        // update
        person.setDateOfBirth(LocalDate.of(1975, 12, 20));
        person = repository.save(person);

        // delete
        repository.delete(person.getId());
        person = repository.get(person.getId());
        Assert.assertNull(person);
    }

    @Transactional
    public void testTransactional() {
        Assert.assertTrue("Transaction must be started", entityManager.getTransaction().isActive());
        testCRUD();
        Assert.assertTrue("Transaction must be started", entityManager.getTransaction().isActive());
    }

    public void testNonTransactional() {
        testCRUD();
    }

    public void testManualTransaction() {
        Assert.assertFalse("Transaction must not be started", entityManager.getTransaction().isActive());
        entityManager.getTransaction().begin();
        testCRUD();
        Assert.assertTrue("Transaction must be started", entityManager.getTransaction().isActive());
        entityManager.getTransaction().rollback();
    }
}
