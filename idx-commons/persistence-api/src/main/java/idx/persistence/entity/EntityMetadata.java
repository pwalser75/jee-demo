package idx.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity metadata
 */
public interface EntityMetadata extends Serializable {

	UUID getUuid();

	int getVersion();

	LocalDateTime getCreatedOn();

	LocalDateTime getLastModifiedOn();

}
