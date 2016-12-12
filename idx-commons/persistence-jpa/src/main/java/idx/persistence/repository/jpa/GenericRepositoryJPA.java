package idx.persistence.repository.jpa;

import idx.persistence.annotation.Generic;
import idx.persistence.entity.Entity;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Generic
@Transactional
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class GenericRepositoryJPA<E extends Entity<?>, ID> implements GenericRepository<E, ID> {

	private Class<E> entityType;

	@Inject
	private EntityManager entityManager;

	@Override
	public void init(Class<E> entityType) {
		this.entityType = entityType;
	}

	@Override
	public Class<E> getEntityType() {
		return entityType;
	}

	@Override
	public E save(E entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Entity is required");
		}

		if (!entity.isPersistent()) {
			// insert
			entityManager.persist(entity);
		} else {
			// update
			entity = entityManager.merge(entity);
		}
		entityManager.flush();
		return entity;
	}

	@Override
	public E get(ID id) {
		if (id == null) {
			throw new IllegalArgumentException("ID is required");
		}
		return entityManager.find(entityType, id);
	}

	@Override
	public List<E> list() {

		CriteriaQuery<E> query = entityManager.getCriteriaBuilder().createQuery(entityType);
		Root root = query.from(entityType);

		query.select(root).distinct(true);

		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public void delete(ID id) {
		if (id == null) {
			throw new IllegalArgumentException("ID is required");
		}
		E entity = get(id);
		if (entity != null) {
			entityManager.remove(entity);
			entityManager.flush();
		}

	}
}
