package idx.contacts.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import idx.contacts.api.model.Person;
import idx.contacts.api.model.Gender;
import idx.contacts.api.service.ContactService;
import idx.persistence.entity.Entity;

@RunWith(CdiTestRunner.class)
public class ContactServiceTest {

	@Inject
	private ContactService contactService;

	@Test
	public void testCRUD() {
		Assert.assertNotNull(contactService);

		Person person = new Person();
		person.setFirstName("Peter");
		person.setLastName("Walser");
		person.setGender(Gender.MALE);
		person.setDateOfBirth(LocalDate.of(1975, 12, 20));

		// create
		Assert.assertNull(person.getId());
		Person persisted = contactService.save(person);
		Assert.assertNotNull(persisted.getId());

		// read: get
		Person loaded = contactService.get(persisted.getId());
		Assert.assertNotNull(loaded);
		Assert.assertEquals(person.getFirstName(), loaded.getFirstName());
		Assert.assertEquals(person.getLastName(), loaded.getLastName());
		Assert.assertEquals(person.getGender(), loaded.getGender());
		Assert.assertEquals(person.getDateOfBirth(), loaded.getDateOfBirth());

		// read: list
		List<Person> list = contactService.list();
		Assert.assertNotNull(list);
		Assert.assertFalse(list.isEmpty());
		Map<Long, Person> listById = list.stream().collect(Collectors.toMap(Person::getId, Function.identity()));
		Person listed = listById.get(persisted.getId());

		Assert.assertNotNull(listed);
		Assert.assertEquals(person.getFirstName(), listed.getFirstName());
		Assert.assertEquals(person.getLastName(), listed.getLastName());
		Assert.assertEquals(person.getGender(), listed.getGender());
		Assert.assertEquals(person.getDateOfBirth(), listed.getDateOfBirth());

		// update
		listed.setFirstName("Manuela");
		listed.setDateOfBirth(LocalDate.of(1979, 10, 24));
		listed.setGender(Gender.FEMALE);
		contactService.save(listed);
		loaded = contactService.get(persisted.getId());
		Assert.assertNotNull(loaded);
		Assert.assertEquals(listed.getFirstName(), loaded.getFirstName());
		Assert.assertEquals(listed.getLastName(), loaded.getLastName());
		Assert.assertEquals(listed.getGender(), loaded.getGender());
		Assert.assertEquals(listed.getDateOfBirth(), loaded.getDateOfBirth());

		// delete
		contactService.delete(loaded.getId());
		loaded = contactService.get(persisted.getId());
		Assert.assertNull(loaded);
	}

	@Test
	public void testInitialDataFound() {

		boolean nathanFound = false;
		boolean colinFound = false;

		for (Person person : contactService.list()) {
			nathanFound = nathanFound || person.getFirstName().equals("Nathan");
			colinFound = colinFound || person.getFirstName().equals("Colin");
		}
		Assert.assertTrue(nathanFound);
		Assert.assertTrue(colinFound);
	}
}
