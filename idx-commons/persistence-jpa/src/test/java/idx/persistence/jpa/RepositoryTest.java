package idx.persistence.jpa;

import idx.persistence.testbase.cdi.TransactionalWrapper;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@RunWith(CdiTestRunner.class)
@Dependent
public class RepositoryTest {

    @Inject
    private RepositoryTestComponent testComponent;

    @Inject
    private TransactionalWrapper tx;

    @Test
    public void testCRUD() {
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
}
