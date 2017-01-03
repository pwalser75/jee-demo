package ${basePackage}.api;

import ${basePackage}.api.model.Note;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;

public class NoteDTOTest {

    @Test
    public void testSerializeJSON() throws IOException {

        Note note = new Note();
        note.setId(12345L);
        note.setText("Aloha");
        note.setCreationDate(LocalDate.of(2000, 1, 1));
        note.setModificationDate(LocalDate.of(2010, 2, 3));

        StringWriter buffer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();

        mapper.setAnnotationIntrospector(
                AnnotationIntrospector.pair(
                        new JacksonAnnotationIntrospector(),
                        new JaxbAnnotationIntrospector(mapper.getTypeFactory())
                )
        );
        mapper.writerWithDefaultPrettyPrinter().writeValue(buffer, note);

        String jsonString = buffer.toString();
        System.out.println(jsonString);

        Assert.assertTrue(jsonString.contains("\"id\" : 12345"));
        Assert.assertTrue(jsonString.contains("\"text\" : \"Aloha\""));
        Assert.assertTrue(jsonString.contains("\"creation_date\" : \"2000-01-01\""));
        Assert.assertTrue(jsonString.contains("\"modification_date\" : \"2010-02-03\""));
    }
}
