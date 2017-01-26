package ch.frostnova.web.example.rest;

import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.Collator;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Example JAX-RS rest web service
 */
@RequestScoped
@Path("/example")
public class ExampleEndpoint {

    static {
        System.out.println("ExampleEndpoint.static initializer");
    }

    /**
     * List system properties
     *
     * @return list of contacts (never null)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject list() {

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        Properties systemProperties = System.getProperties();
        List<String> keys = new LinkedList<>(systemProperties.stringPropertyNames());
        Collections.sort(keys, Collator.getInstance());
        for (String key : keys) {
            objectBuilder.add(key, systemProperties.getProperty(key));
        }

        return objectBuilder.build();

    }

}
