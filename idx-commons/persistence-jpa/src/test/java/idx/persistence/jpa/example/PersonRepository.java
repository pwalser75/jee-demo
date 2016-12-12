package idx.persistence.jpa.example;

import idx.persistence.annotation.Specific;
import idx.persistence.repository.jpa.GenericRepositoryJPA;

import javax.transaction.Transactional;

@Specific(type = Person.class)
@Transactional
public class PersonRepository extends GenericRepositoryJPA<Person, Long> {

	@Override
	public Class<Person> getEntityType() {
		return Person.class;
	}

	@Override
	public Person save(Person entity) {
		System.out.println("Saving person");
		return super.save(entity);
	}
}
