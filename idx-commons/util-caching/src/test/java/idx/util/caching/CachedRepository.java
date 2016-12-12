package idx.util.caching;

import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheRemoveAll;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;
import java.util.HashMap;
import java.util.Map;

/**
 * Repository mock with caching
 */
@CacheDefaults(cacheName = "test")
public class CachedRepository<K, V> {

    private int loadCount;

    private final Map<K, V> repository = new HashMap<>();

    @CachePut
    public void store(@CacheKey K key, @CacheValue V value) {
        repository.put(key, value);
    }

    @CacheResult
    public V load(@CacheKey K key) {
        loadCount++;
        return repository.get(key);
    }

    @CacheRemove
    public void delete(@CacheKey K key) {
        repository.remove(key);
    }

    public int getLoadCount() {
        return loadCount;
    }

    @CacheRemoveAll
    public void clearCache() {

    }
}
