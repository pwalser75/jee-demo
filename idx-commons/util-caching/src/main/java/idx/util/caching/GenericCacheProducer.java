package idx.util.caching;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.Configuration;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.net.URI;

/**
 * CDI factory/registry, produces injectable cache instances.
 */
@ApplicationScoped
public class GenericCacheProducer {

    private final static Logger logger = LoggerFactory.getLogger(GenericCacheProducer.class);

    private final static String CACHE_CONFIG = "/ehcache.xml";

    private URI getCacheConfig() {
        try {
            return getClass().getResource(CACHE_CONFIG).toURI();
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to get cache config from " + CACHE_CONFIG, ex);
        }
    }

    @Produces
    @NamedCache
    public <K, V> Cache<K, V> createCache(InjectionPoint injectionPoint) {

        final CachingProvider cachingProvider = Caching.getCachingProvider();
        final CacheManager cacheManager = Caching.getCachingProvider().getCacheManager(
                getCacheConfig(),
                getClass().getClassLoader());

        final NamedCache annotation = injectionPoint.getAnnotated().getAnnotation(NamedCache.class);
        final String name = annotation.name();

        Cache cache = cacheManager.getCache(name);
        if (cache == null) {
            logger.debug("creating cache: " + name);
            final Configuration config = createConfiguration();
            cache = cacheManager.createCache(name, config);
        } else {
            logger.debug("using preconfigured cache: " + name);
        }
        return cache;
    }

    private Configuration createConfiguration() {
        MutableConfiguration config = new MutableConfiguration<>();
        config.setTypes(Object.class, Object.class);
        config.setStoreByValue(true);
        config.setStatisticsEnabled(true);
        config.setManagementEnabled(true);
        return config;
    }
}
