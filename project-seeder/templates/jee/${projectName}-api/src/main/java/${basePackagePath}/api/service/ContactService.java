package ${basePackage}.api.service;

        import java.util.List;

        import ${basePackage}.api.model.Person;

public interface ContactService {

    Person get(long id);

    Person save(Person person);

    List<Person> list();

    void delete(long id);

}
