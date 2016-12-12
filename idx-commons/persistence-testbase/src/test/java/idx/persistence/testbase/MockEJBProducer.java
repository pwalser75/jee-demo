package idx.persistence.testbase;

import idx.persistence.testbase.ejb.TestUnavailableEJB;
import org.mockito.Mockito;

import javax.enterprise.inject.Produces;

public class MockEJBProducer {

    @Produces
    public TestUnavailableEJB createUnavailableEJB() {
        return Mockito.mock(TestUnavailableEJB.class);
    }
}
