package idx.contacts.api;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import idx.contacts.api.model.Person;
import idx.contacts.api.model.Gender;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.Period;

public class PersonDTOTest {

	@Test
	public void testAge() {
		Person dto = new Person();

		Assert.assertNull(dto.getAge());

		dto.setFirstName("Peter");
		dto.setLastName("Walser");
		dto.setGender(Gender.MALE);
		dto.setDateOfBirth(LocalDate.of(1975, 12, 20));

		Period age = dto.getAge();
		System.out.println(age.getYears() + " years, " + age.getMonths() + " months, " + age.getDays() + " days");
	}

	@Test
	public void testSerializeJSON() throws IOException {

		Person person = new Person();
		person.setId(12345L);
		person.setFirstName("Peter");
		person.setLastName("Walser");
		person.setGender(Gender.MALE);
		person.setDateOfBirth(LocalDate.of(1975, 12, 20));

		StringWriter buffer = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();

		mapper.setAnnotationIntrospector(
				AnnotationIntrospector.pair(
						new JacksonAnnotationIntrospector(),
						new JaxbAnnotationIntrospector(mapper.getTypeFactory())
				)
		);
		mapper.writerWithDefaultPrettyPrinter().writeValue(buffer, person);

		String jsonString = buffer.toString();
		System.out.println(jsonString);

		Assert.assertTrue(jsonString.contains("\"id\" : 12345"));
		Assert.assertTrue(jsonString.contains("\"first_name\" : \"Peter\""));
		Assert.assertTrue(jsonString.contains("\"last_name\" : \"Walser\""));
		Assert.assertTrue(jsonString.contains("\"gender\" : \"MALE\""));
		Assert.assertTrue(jsonString.contains("\"birth_date\" : \"1975-12-20\""));

	}
}
