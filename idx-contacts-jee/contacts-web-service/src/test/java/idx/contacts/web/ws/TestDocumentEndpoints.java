package idx.contacts.web.ws;

import com.google.common.reflect.ClassPath;
import org.junit.Test;

import java.util.function.Predicate;

/**
 * Test: find endpoints and document them.
 *
 * @author wap
 * @since 13.12.2016
 */
public class TestDocumentEndpoints {

    @Test
    public void documentEndpoints() throws Exception {

        Predicate<ClassPath.ClassInfo> filter = info -> (info.getPackageName().startsWith("ch.") || info.getPackageName().startsWith("idx.")) && info.getName().endsWith("Endpoint");
        WebServiceEndpointIntrospection.documentEndpoints(filter);
    }
}
