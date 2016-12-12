package idx.persistence.entity;

import java.io.Serializable;

/**
 * Entity contract: entities have an ID and are serializable.
 */
public interface Entity<ID> extends Serializable {

	ID getId();

	default Class<?> getEntityType() {
		return getClass();
	}

	default boolean isPersistent() {
		return getId() != null;
	}
}