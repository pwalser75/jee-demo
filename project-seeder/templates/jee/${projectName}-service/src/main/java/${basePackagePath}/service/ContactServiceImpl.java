package ${basePackage}.service;

        import java.util.List;
        import java.util.stream.Collectors;

        import javax.ejb.Stateless;
        import javax.inject.Inject;

        import ${basePackage}.api.model.Person;
        import ${basePackage}.api.service.ContactService;
        import ${basePackage}.persistence.entity.PersonEntity;
        import idx.persistence.repository.Repository;

@Stateless
public class ContactServiceImpl implements ContactService {

    @Inject
    private Repository<PersonEntity, Long> repository;


    @Override
    public Person get(long id) {
        return convert(repository.get(id));
    }

    @Override
    public Person save(Person person) {
        PersonEntity entity = new PersonEntity();
        if (person.getId() != null) {
            entity = repository.get(person.getId());
        }
        return convert(repository.save(update(entity, person)));
    }

    @Override
    public List<Person> list() {
        List<PersonEntity> list = repository.list();
        return list.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
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
