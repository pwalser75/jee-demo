package idx.persistence.testbase.cdi;

import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import java.util.concurrent.Executors;

/**
 * @author pwalser
 * @since 27.05.2016.
 */
public class ManagedExecutorServiceProducer {

    @Produces
    @RequestScoped
    public ManagedExecutorService create() {
        return new TestManagedExecutorService(Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()));
    }
}
