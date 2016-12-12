package idx.persistence.repository.jpa;

import idx.persistence.entity.Entity;
import idx.persistence.repository.Repository;

public interface GenericRepository<E extends Entity<?>, ID> extends Repository<E, ID> {

	void init(Class<E> entityType);
}
