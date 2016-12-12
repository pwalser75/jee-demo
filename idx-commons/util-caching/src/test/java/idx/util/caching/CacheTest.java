package idx.util.caching;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.cache.Cache;
import javax.inject.Inject;

/**
 * Test for JCache
 */
@RunWith(CdiTestRunner.class)
public class CacheTest {

    @Inject
    @NamedCache
    private Cache<Integer, String> defaultCache;

    @Inject
    @NamedCache(name = "test")
    private Cache<Integer, String> testCache;

    @Inject
    @NamedCache(name = "quick")
    private Cache<Integer, String> quickCache;

    @Inject
    @NamedCache(name = "tiny")
    private Cache<Integer, String> tinyCache;

    @Inject
    private CachedRepository<Integer, String> repository;

    @Test
    public void testCacheCreation() {
        Assert.assertNotNull(defaultCache);
        Assert.assertNotNull(testCache);
    }

    @Test
    public void testDirectCacheUsage() {

        testCache.put(1, "Hello");
        testCache.put(2, "Aloha");

        Assert.assertEquals("Hello", testCache.get(1));
        Assert.assertEquals("Aloha", testCache.get(2));
        Assert.assertNull(testCache.get(3));

        testCache.put(1, "Ciao");
        testCache.remove(2);

        Assert.assertEquals("Ciao", testCache.get(1));
        Assert.assertNull(testCache.get(2));

        testCache.clear();
        Assert.assertNull(testCache.get(1));
    }

    @Test
    public void testCachedBean() {

        int expectedLoadCount = repository.getLoadCount();

        // write-through
        repository.store(1, "Hello");

        // cached read, load count not incremented
        Assert.assertEquals("Hello", repository.load(1));
        Assert.assertEquals(expectedLoadCount, repository.getLoadCount());

        // uncached read, followed by cached read
        repository.clearCache();
        Assert.assertEquals("Hello", repository.load(1));
        Assert.assertEquals(++expectedLoadCount, repository.getLoadCount());
        Assert.assertEquals("Hello", repository.load(1));
        Assert.assertEquals(expectedLoadCount, repository.getLoadCount());

        // change cached entry (write-though)
        repository.store(1, "Aloha");
        Assert.assertEquals("Aloha", repository.load(1));
        Assert.assertEquals(expectedLoadCount, repository.getLoadCount());

        // remove cached entry
        repository.delete(1);
        Assert.assertNull(repository.load(1));
        Assert.assertEquals(++expectedLoadCount, repository.getLoadCount());

        // clear cache
        repository.store(1, "Hello");
        repository.store(2, "Aloha");
        repository.store(3, "Ciao");
        Assert.assertEquals("Hello", repository.load(1));
        Assert.assertEquals("Aloha", repository.load(2));
        Assert.assertEquals("Ciao", repository.load(3));
        Assert.assertEquals(expectedLoadCount, repository.getLoadCount());
        repository.clearCache();
        Assert.assertEquals("Hello", repository.load(1));
        Assert.assertEquals("Aloha", repository.load(2));
        Assert.assertEquals("Ciao", repository.load(3));
        Assert.assertEquals(expectedLoadCount += 3, repository.getLoadCount());
    }

    /**
     * Tests a cache whose entires expire after one second
     */
    @Test
    public void testQuickCache() throws Exception {
        quickCache.put(1, "Aloha");
        Assert.assertEquals("Aloha", quickCache.get(1));

        // wait half a second. Expected: entry is still there
        Thread.sleep(500);
        Assert.assertNotNull(quickCache.get(1));
        Assert.assertEquals("Aloha", quickCache.get(1));

        // wait one second more. Expected: entry is no longer there
        Thread.sleep(1000);
        Assert.assertNull(quickCache.get(1));
    }

    /**
     * Tests a cache which can hold only 5 entries.
     */
    @Test
    public void testTinyCache() {

        tinyCache.put(1, "Hello");
        tinyCache.put(2, "Aloha");
        tinyCache.put(3, "Hallo");
        tinyCache.put(4, "Salut");
        tinyCache.put(5, "Ciao");
        tinyCache.put(6, "Holà");

        int entriesFoundInCache = 0;
        for (int i = 1; i <= 6; i++) {
            if (tinyCache.get(i) != null) {
                entriesFoundInCache++;
            }
        }
        Assert.assertEquals(5, entriesFoundInCache);
    }
}
