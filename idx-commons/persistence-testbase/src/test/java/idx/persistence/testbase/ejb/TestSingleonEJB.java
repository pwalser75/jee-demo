package idx.persistence.testbase.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class TestSingleonEJB {

    private static boolean initialized;

    @EJB
    private TestStatelessEJB statelessEJB;

    public Double getValue() {
        return statelessEJB.getValue();
    }

    @PostConstruct
    public void postConstruct() {
        if (initialized) {
            throw new IllegalStateException("@Singleton EJB already has been initialized");
        }
        initialized = true;
    }

    @PreDestroy
    public void preDestroy() {
        if (!initialized) {
            throw new IllegalStateException("@PreDestroy without @PostConstruct");
        }
        initialized = false;
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
