package idx.contacts.api.service;

import java.util.List;

import idx.contacts.api.model.Person;

public interface ContactService {

	Person get(long id);

	Person save(Person person);

	List<Person> list();

	void delete(long id);

}
