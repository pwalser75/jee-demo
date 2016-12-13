package idx.contacts.web.ws;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Test util class for web service endpoint introspection
 *
 * @author Peter Walser
 * @since 13.12.2016
 */
public final class WebServiceEndpointIntrospection {

    private WebServiceEndpointIntrospection() {

    }

    /**
     * Discover and document all web service endpoints using reflection (output to System.out)
     */
    public static void documentEndpoints(Predicate<ClassPath.ClassInfo> filter) throws Exception {
        documentEndpoints(filter, new PrintWriter(System.out));
    }

    /**
     * Discover and document all web service endpoints using reflection.
     */
    public static void documentEndpoints(Predicate<ClassPath.ClassInfo> filter, PrintWriter out) throws Exception {

        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final ClassPath classPath = ClassPath.from(classLoader);
        final ImmutableSet<ClassPath.ClassInfo> allClasses = classPath.getAllClasses();
        allClasses.forEach(info -> {
            if (filter == null || filter.test(info)) {
                Class<?> c = info.load();
                if (!Modifier.isAbstract(c.getModifiers()) && !c.isInterface()) {
                    documentEndpoint(c, out);
                }
            }
        });
    }

    /**
     * Document a web service endpoint using reflection.
     *
     * @param c endpoint class
     */
    public static void documentEndpoint(Class<?> c, PrintWriter out) {
        out.println(c.getName());

        Path basePath = c.getAnnotation(Path.class);
        for (Method method : c.getMethods()) {

            Path methodPath = method.getAnnotation(Path.class);

            GET get = method.getAnnotation(GET.class);
            POST post = method.getAnnotation(POST.class);
            PUT put = method.getAnnotation(PUT.class);
            DELETE delete = method.getAnnotation(DELETE.class);
            HEAD head = method.getAnnotation(HEAD.class);
            OPTIONS options = method.getAnnotation(OPTIONS.class);

            Consumes consumes = method.getAnnotation(Consumes.class);
            Produces produces = method.getAnnotation(Produces.class);

            boolean isWebServiceMethod = Arrays.asList(get, post, put, delete, head, options).stream().anyMatch(Objects::nonNull);

            if (isWebServiceMethod) {

                String path = Arrays.asList(basePath, methodPath).stream().filter(Objects::nonNull).map(p -> p.value()).collect(Collectors.joining());
                String methods = Arrays.asList(get, post, put, delete, head, options).stream().filter(Objects::nonNull).map(p -> p.annotationType().getSimpleName()).collect(Collectors.joining(", "));

                out.println("- " + method.getName());
                out.println("     path: '" + path + "'");
                out.println("     method: " + methods);
                if (consumes != null) {
                    out.println("     consumes: " + Stream.of(consumes.value()).collect(Collectors.joining(", ")));
                }
                if (produces != null) {
                    out.println("     produces: " + Stream.of(produces.value()).collect(Collectors.joining(", ")));
                }
                for (Parameter parameter : method.getParameters()) {
                    FormParam formParam = parameter.getAnnotation(FormParam.class);
                    HeaderParam headerParam = parameter.getAnnotation(HeaderParam.class);
                    PathParam pathParam = parameter.getAnnotation(PathParam.class);
                    QueryParam queryParam = parameter.getAnnotation(QueryParam.class);
                    CookieParam cookieParam = parameter.getAnnotation(CookieParam.class);

                    String params = Arrays.asList(formParam, headerParam, pathParam, queryParam, cookieParam).stream().filter(Objects::nonNull).map(p -> p.annotationType().getSimpleName() + " '" + getParamName(p) + "'").collect(Collectors.joining(", "));
                    if (params.trim().length() == 0) {
                        params = "Body";
                    }

                    out.println("     " + params + " (" + parameter.getType().getSimpleName() + ")");
                }
                if (method.getReturnType() != null && !Void.TYPE.equals(method.getReturnType())) {
                    out.println("     returns: " + method.getReturnType().getSimpleName());
                }
            }
        }
        out.flush();
    }

    private static String getParamName(Object paramAnnotation) {
        if (paramAnnotation instanceof FormParam) {
            return ((FormParam) paramAnnotation).value();
        }
        if (paramAnnotation instanceof HeaderParam) {
            return ((HeaderParam) paramAnnotation).value();
        }
        if (paramAnnotation instanceof PathParam) {
            return ((PathParam) paramAnnotation).value();
        }
        if (paramAnnotation instanceof QueryParam) {
            return ((QueryParam) paramAnnotation).value();
        }
        if (paramAnnotation instanceof CookieParam) {
            return ((CookieParam) paramAnnotation).value();
        }

        return "?";
    }
}
