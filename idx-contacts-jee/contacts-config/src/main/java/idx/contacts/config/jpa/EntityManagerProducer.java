package idx.contacts.config.jpa;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Producer to make the entity manager available for injection.
 */
public class EntityManagerProducer {

    @Produces
    @Dependent
    @PersistenceContext(unitName = "contacts")
    private EntityManager entityManager;

}
