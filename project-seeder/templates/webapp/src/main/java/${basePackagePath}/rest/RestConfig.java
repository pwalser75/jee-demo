package ch.frostnova.web.example.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class RestConfig extends Application {
    static {
        System.out.println("RestConfig.static initializer");
    }

}
