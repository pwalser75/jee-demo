package idx.contacts.service;

import idx.contacts.api.model.Person;
import idx.contacts.api.service.ContactService;
import idx.contacts.persistence.entity.PersonEntity;
import idx.contacts.persistence.entity.PersonRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Stateless
public class ContactServiceImpl implements ContactService {

    @Inject
    private PersonRepository repository;


    @Override
    public Person get(long id) {
        return convert(repository.findOne(id));
    }

    @Override
    public Person save(Person person) {
        PersonEntity entity = new PersonEntity();
        if (person.getId() != null) {
            entity = repository.findOne(person.getId());
        }
        return convert(repository.save(update(entity, person)));
    }

    @Override
    public List<Person> list() {
        Spliterator<PersonEntity> spliterator = repository.findAll().spliterator();
        Stream<PersonEntity> stream = StreamSupport.stream(spliterator, false);
        return stream.map(this::convert).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        if (repository.exists(id)) {
            repository.delete(id);
        }
    }

    private Person convert(PersonEntity entity) {
        if (entity == null) {
            return null;
        }
        Person dto = new Person();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setGender(entity.getGender());
        dto.setDateOfBirth(entity.getDateOfBirth());
        return dto;
    }

    private PersonEntity update(PersonEntity entity, Person dto) {
        if (dto == null) {
            return null;
        }
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setGender(dto.getGender());
        entity.setDateOfBirth(dto.getDateOfBirth());
        return entity;
    }
}
