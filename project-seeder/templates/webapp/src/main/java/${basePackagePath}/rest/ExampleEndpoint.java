package ${basePackage}.rest;

        import javax.enterprise.context.RequestScoped;
        import javax.json.Json;
        import javax.json.JsonObjectBuilder;
        import javax.json.JsonStructure;
        import javax.json.JsonWriter;
        import javax.ws.rs.GET;
        import javax.ws.rs.Path;
        import javax.ws.rs.Produces;
        import javax.ws.rs.core.MediaType;
        import java.io.StringWriter;
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

    /**
     * List system properties and return them as JSON object
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String list() {

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        Properties systemProperties = System.getProperties();
        List<String> keys = new LinkedList<>(systemProperties.stringPropertyNames());
        Collections.sort(keys, Collator.getInstance());
        for (String key : keys) {
            String value = systemProperties.getProperty(key);
            objectBuilder.add(key, value);
        }

        return serializeJSON(objectBuilder.build());
    }

    private String serializeJSON(JsonStructure jsonStructure) {
        if (jsonStructure == null) {
            return null;
        }
        StringWriter writer = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(writer);
        jsonWriter.write(jsonStructure);
        jsonWriter.close();
        return writer.toString();
    }

}
