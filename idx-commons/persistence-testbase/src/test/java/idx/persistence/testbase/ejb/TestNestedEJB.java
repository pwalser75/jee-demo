package idx.persistence.testbase.ejb;

import javax.ejb.Stateless;

/**
 * @author pwalser
 * @since 28.06.2016.
 */
@Stateless
public class TestNestedEJB {

    public void sendMessage(String message) {
        System.out.println("Message from " + getClass().getSimpleName() + ": " + message);
    }

}
