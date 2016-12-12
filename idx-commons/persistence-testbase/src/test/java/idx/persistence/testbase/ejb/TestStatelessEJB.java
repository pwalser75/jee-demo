package idx.persistence.testbase.ejb;

import javax.ejb.Stateless;
import java.time.LocalDate;

@Stateless
public class TestStatelessEJB {

    public LocalDate getDate() {
        return LocalDate.now();
    }

    public Double getValue() {
        return Math.PI;
    }

}
