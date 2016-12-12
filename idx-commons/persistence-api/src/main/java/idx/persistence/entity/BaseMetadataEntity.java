package idx.persistence.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Base entity implementation for entities with metadata.
 */
@SuppressWarnings("serial")
public abstract class BaseMetadataEntity<ID> implements Entity<ID>, EntityMetadata {

	private ID id;
	private UUID uuid;
	private int version;
	private LocalDateTime createdOn;
	private LocalDateTime lastModifiedOn;

	public BaseMetadataEntity() {
		uuid = UUID.randomUUID();
	}

	@Override
	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	@Override
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public LocalDateTime getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(LocalDateTime lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	@Override
	public final boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!getClass().equals(obj.getClass())) {
			return false;
		}
		EntityMetadata other = (EntityMetadata) obj;
		return Objects.equals(uuid, other.getUuid());
	}

	@Override
	public final int hashCode() {
		return Objects.hash(getClass().getName(), getUuid());
	}

	@Override
	public String toString() {
		return getClass().getName() + "#" + getId();
	}
}