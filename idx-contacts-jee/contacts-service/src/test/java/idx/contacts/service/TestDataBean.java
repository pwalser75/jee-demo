package idx.contacts.service;

import ch.frostnova.jee.testbase.TransactionalWrapper;
import idx.contacts.api.model.Gender;
import idx.contacts.persistence.entity.PersonEntity;
import idx.contacts.persistence.entity.PersonRepository;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.time.LocalDate;

/**
 * Creates test data for the persistence tests.
 */
@Singleton
@Startup
public class TestDataBean {

    @Inject
    private PersonRepository repository;

    @Inject
    TransactionalWrapper transactionalWrapper;

    @Inject
    Logger logger;

    @PostConstruct
    public void initTestData() throws Exception {

        logger.info("Creating test data");

        transactionalWrapper.execute(() -> {

            PersonEntity person = new PersonEntity();
            person.setFirstName("Nathan");
            person.setLastName("Walser");
            person.setGender(Gender.MALE);
            person.setDateOfBirth(LocalDate.of(2006, 2, 8));
            repository.save(person);

            person = new PersonEntity();
            person.setFirstName("Colin");
            person.setLastName("Walser");
            person.setGender(Gender.MALE);
            person.setDateOfBirth(LocalDate.of(2008, 6, 11));
            repository.save(person);

        });
    }

    @PreDestroy
    public void cleanupTestData() throws Exception {
        logger.info("Destroying test data");
        repository.deleteAll();
    }
}
