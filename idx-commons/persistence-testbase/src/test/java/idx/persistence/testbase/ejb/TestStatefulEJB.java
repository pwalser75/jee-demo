package idx.persistence.testbase.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;

@Stateful
public class TestStatefulEJB {

    private String name;
    private Long constructionTimeNs;

    public void setName(String name) {
        this.name = name;
    }

    public String hello() {
        return "Aloha " + (name != null ? name : "whoever you are");
    }

    @PostConstruct
    private void init() {
        constructionTimeNs = System.nanoTime();
    }

    public Long getConstructionTimeNs() {
        return constructionTimeNs;
    }
}
