package idx.persistence.testbase;

import idx.persistence.testbase.ejb.TestStatelessEJB;
import idx.persistence.testbase.ejb.TestUnavailableEJB;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.time.LocalDate;

@RunWith(CdiTestRunner.class)
public class CDITest {

    @Inject
    private TestStatelessEJB testEJB;

    @Inject
    private TestUnavailableEJB unavailableEJB;

    @Test
    public void testDirectEJBCall() {
        Assert.assertEquals(LocalDate.now(), testEJB.getDate());
    }

    @Test
    public void testIndirectEJBCall() {
        Assert.assertEquals(Double.valueOf(Math.PI), testEJB.getValue());
    }

    @Test
    public void testMockedUnavailableEJB() {

        Mockito.when(unavailableEJB.getMessage()).thenReturn("Aloha");
        Assert.assertEquals("Aloha", unavailableEJB.getMessage());
    }
}
