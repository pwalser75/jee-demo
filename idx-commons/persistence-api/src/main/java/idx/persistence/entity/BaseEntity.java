package idx.persistence.entity;

import java.util.Objects;

/**
 * Base entity implementation.
 */
public abstract class BaseEntity<ID> implements Entity<ID> {

    private ID id;

    @Override
    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        BaseEntity<?> other = (BaseEntity<?>) obj;
        if (!isPersistent()) {
            return other == this;
        }
        return Objects.equals(getId(), other.getId());
    }

    @Override
    public final int hashCode() {
        if (!isPersistent()) {
            return super.hashCode();
        }
        return Objects.hash(getClass().getName(), getId());
    }

    @Override
    public String toString() {
        return getClass().getName() + "#" + getId();
    }
}