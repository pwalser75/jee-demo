package idx.contacts.persistence.entity;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by pwalser on 04.02.2017.
 */
public interface PersonRepository extends CrudRepository<PersonEntity, Long> {
}
