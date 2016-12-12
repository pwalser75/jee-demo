package idx.util.caching;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for cache injection. The cache to be used is specified by name,
 * if the name is absent, the cache named 'defaultCache' will be used.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface NamedCache {
    String name() default "defaultCache";
}
