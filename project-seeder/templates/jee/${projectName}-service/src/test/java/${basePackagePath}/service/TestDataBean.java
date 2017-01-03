package ${basePackage}.service;

import ${basePackage}.persistence.entity.NoteEntity;
import idx.persistence.repository.Repository;
import idx.persistence.testbase.cdi.TransactionalWrapper;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Creates test data for the persistence tests.
 */
@Singleton
@Startup
public class TestDataBean {

    @Inject
    private Repository<NoteEntity, Long> repository;

    @Inject
    TransactionalWrapper transactionalWrapper;

    @Inject
    Logger logger;

    @PostConstruct
    public void initTestData() throws Exception {

        logger.info("Creating test data");

        transactionalWrapper.execute(() -> {

            NoteEntity note = new NoteEntity();
            note.setText("Bonjour");
            repository.save(note);

            note = new NoteEntity();
            note.setText("Welcome");
            repository.save(note);

        });
    }

    @PreDestroy
    public void cleanupTestData() throws Exception {
        logger.info("Destroying test data");
        for (NoteEntity p : repository.list()) {
            repository.delete(p.getId());
        }
    }
}
