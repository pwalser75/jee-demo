package idx.persistence.repository.jpa;

import idx.persistence.annotation.Generic;
import idx.persistence.annotation.SpecificLiteral;
import idx.persistence.entity.Entity;
import idx.persistence.repository.Repository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.lang.reflect.ParameterizedType;

@ApplicationScoped
public class RepositoryProducer {

	private final Logger log = Logger.getLogger(RepositoryProducer.class);

	@Produces
	@Dependent
	public <E extends Entity<?>, ID> Repository<E, ID> create(final InjectionPoint injectionPoint,
			@Generic final GenericRepository<E, ID> genericRepository,
			@Any Instance<Repository<E, ID>> specificRepositories) {

		final ParameterizedType type = (ParameterizedType) injectionPoint.getType();
		final Class<E> entityType = (Class<E>) type.getActualTypeArguments()[0];

		log.debug("Creating repository for entity type: " + entityType.getName());
		// look for a specific implementation
		Instance<Repository<E, ID>> specificInstance = specificRepositories.select(new SpecificLiteral(entityType));
		if (specificInstance != null) {
			String message = "specific repository instance for entity type: " + entityType.getName();
			if (specificInstance.isAmbiguous()) {
				log.debug("Ambigous " + message + ", falling back to default");
			} else if (!specificInstance.isUnsatisfied()) {
				log.debug("Found " + message);
				return specificInstance.get();
			}
		}
		genericRepository.init(entityType);
		return genericRepository;
	}
}
