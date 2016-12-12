package idx.contacts.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * CDI factory for creating slf4j-Logger instances.<br/>
 * Usage:
 * <code>
 * <pre>
 *         &#64;Inject
 *         private Logger logger;
 * </pre>
 * </code>
 */
public class LoggerProducer {

    @Produces
    public Logger createLogger(InjectionPoint point) {

        Class<?> beanClass = point.getBean().getBeanClass();
        return LoggerFactory.getLogger(beanClass);

    }
}
