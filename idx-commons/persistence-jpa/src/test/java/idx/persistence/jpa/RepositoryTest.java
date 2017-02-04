package idx.persistence.jpa;

import idx.persistence.exception.ConstraintViolationException;
import idx.persistence.jpa.example.Gender;
import idx.persistence.jpa.example.Person;
import idx.persistence.jpa.example.Value;
import idx.persistence.repository.Repository;
import idx.persistence.testbase.cdi.TransactionalWrapper;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.time.LocalDate;

@RunWith(CdiTestRunner.class)
@Dependent
public class RepositoryTest {

    @Inject
    private RepositoryTestComponent testComponent;

    @Inject
    private TransactionalWrapper tx;

    @Inject
    private Repository<Person, Long> repository;

    @Test(expected = ConstraintViolationException.class)
    public void testNonNullConstraints() throws Exception {

        Person person = new Person();
        try {
            repository.save(person);
        } catch (PersistenceException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Test
    public void testCrud() throws Exception {

        final Value<Long> id = new Value<>();

        tx.execute(() -> {
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
            id.set(person.getId());
        });

        // read
        tx.execute(() -> {
            Person person = repository.get(id.get());
            Assert.assertEquals("Peter", person.getFirstName());
            Assert.assertEquals("Walser", person.getLastName());
            Assert.assertEquals("user@test.org", person.getEmailAddress());
            Assert.assertEquals(Gender.MALE, person.getGender());
        });
        // update
        tx.execute(() -> {
            Person person = repository.get(id.get());
            person.setDateOfBirth(LocalDate.of(1975, 12, 20));
            repository.save(person);
        });

        // delete
        tx.execute(() -> {
            repository.delete(id.get());
        });

        tx.execute(() -> {
            Person person = repository.get(id.get());
            Assert.assertNull(person);
        });
    }

    @Test
    public void testCrudDelegate() {
        testComponent.testCRUD();
    }

    @Test
    public void testTransactional() {
        testComponent.testTransactional();
    }

    @Test
    public void testNonTransactional() {
        testComponent.testNonTransactional();
    }

    @Test
    public void testManualTransaction() {
        testComponent.testManualTransaction();
    }

    @Test
    public void testTransactionWrapper() throws Exception {
        tx.execute(() -> testComponent.testNonTransactional());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testDuplicateException() throws Exception {
        try {
            tx.execute(() -> {
                Person person = new Person();
                person.setFirstName("Some");
                person.setLastName("Person");
                person.setEmailAddress("user@foo.org");
                person.setGender(Gender.MALE);
                repository.save(person);
            });

            tx.execute(() -> {
                Person person = new Person();
                person.setFirstName("Another");
                person.setLastName("Person");
                person.setEmailAddress("user@foo.org");
                person.setGender(Gender.FEMALE);
                repository.save(person);
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void testConstraintViolationException() throws Exception {
        try {
            tx.execute(() -> {
                Person person = new Person();
                person.setFirstName("This name is waaaaaaaaaaaaaaaay too long");
                person.setLastName("This oke's ok");
                person.setGender(Gender.MALE);

                repository.save(person);
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
