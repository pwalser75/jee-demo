package idx.persistence.repository;

import idx.persistence.entity.Entity;

import java.util.List;

public interface Repository<E extends Entity<?>, ID> {

	Class<E> getEntityType();

	E save(E person);

	E get(ID id);

	List<E> list();

	void delete(ID id);
}
