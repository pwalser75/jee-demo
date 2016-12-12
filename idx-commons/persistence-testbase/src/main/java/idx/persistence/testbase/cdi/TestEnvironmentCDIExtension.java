package idx.persistence.testbase.cdi;

import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.deltaspike.core.util.metadata.AnnotationInstanceProvider;
import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessBean;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.logging.LogManager;

public class TestEnvironmentCDIExtension implements Extension {

    private final Logger log = LoggerFactory.getLogger(TestEnvironmentCDIExtension.class);

    private final Set<Bean<?>> startupBeans = new LinkedHashSet<>();

    public <X> void processInjectionTarget(@Observes ProcessAnnotatedType<X> processAnnotatedType) {

        AnnotatedType<X> annotatedType = processAnnotatedType.getAnnotatedType();

        org.apache.deltaspike.jpa.api.transaction.Transactional deltaspikeTransactional = AnnotationInstanceProvider.of(org.apache.deltaspike.jpa.api.transaction.Transactional.class);

        AnnotatedTypeBuilder<X> builder = new AnnotatedTypeBuilder<X>().readFromType(annotatedType);

        boolean modified = false;

        // replace CDI @Transactional with Deltaspike @Transactional
        if (annotatedType.isAnnotationPresent(Transactional.class)) {
            log.debug("processing @Transactional class: " + processAnnotatedType);
            builder.addToClass(deltaspikeTransactional);
            modified = true;
        }
        for (AnnotatedMethod<? super X> method : annotatedType.getMethods()) {
            if (method.isAnnotationPresent(Transactional.class)) {
                log.debug("processing @Transactional method: " + method);
                builder.addToMethod(method, deltaspikeTransactional);
                modified = true;
            }
        }

        // for EJB @Singleton: add CDI @ApplicationScoped
        if (annotatedType.isAnnotationPresent(Singleton.class)) {
            log.debug("processing @Singleton class: " + processAnnotatedType);
            builder.addToClass(AnnotationInstanceProvider.of(Stateful.class));
            builder.addToClass(AnnotationInstanceProvider.of(ApplicationScoped.class));
            builder.addToClass(AnnotationInstanceProvider.of(Transactional.class));
            builder.addToClass(deltaspikeTransactional);
            modified = true;
        }

        // make all EJBs transactional
        if (annotatedType.isAnnotationPresent(Stateful.class) || annotatedType.isAnnotationPresent(Stateless.class) || annotatedType.isAnnotationPresent(Singleton.class) || annotatedType.isAnnotationPresent(MessageDriven.class)) {
            log.debug("processing EJB class: " + processAnnotatedType);
            builder.addToClass(deltaspikeTransactional);
            modified = true;
        }

        // injection for ExecutorService
        for (AnnotatedField<? super X> field : annotatedType.getFields()) {
            if (field.isAnnotationPresent(Resource.class) && ExecutorService.class.isAssignableFrom(field.getJavaMember().getType())) {
                builder.addToField(field, AnnotationInstanceProvider.of(Inject.class));
                modified = true;
            }
        }

        if (annotatedType.isAnnotationPresent(Stateful.class) || annotatedType.isAnnotationPresent(Stateless.class) || annotatedType.isAnnotationPresent(Singleton.class) || annotatedType.isAnnotationPresent(MessageDriven.class)) {
            log.debug("processing EJB class: " + processAnnotatedType);
            builder.addToClass(deltaspikeTransactional);
            modified = true;
        }

        if (modified) {
            processAnnotatedType.setAnnotatedType(builder.create());
        }
    }

    private <X> void processBean(@Observes ProcessBean<X> event) {

        // Track @Startup beans and initialize them later (on AfterDeploymentValidation).
        Annotated annotated = event.getAnnotated();
        if (annotated.isAnnotationPresent(Startup.class) && (annotated.isAnnotationPresent(Singleton.class) || annotated.isAnnotationPresent(ApplicationScoped.class))) {
            log.debug("tracking @Startup bean: " + event.getBean().getBeanClass());
            startupBeans.add(event.getBean());
        }
    }

    private void afterDeploymentValidation(@Observes AfterDeploymentValidation event, BeanManager manager) {

        initLogging();

        // spring data CDI integration performs an after-deployment validation which already requires an active @RequestScoped context.
        // the fault here is with spring's CDI integration, but as a workaround we start such a context here.
        ContextControl ctxCtrl = BeanProvider.getContextualReference(ContextControl.class);
        ctxCtrl.startContext(ApplicationScoped.class);
        ctxCtrl.startContext(RequestScoped.class);

        // start @Startup beans
        for (Bean<?> bean : startupBeans) {
            log.debug("starting @Startup bean: " + bean.getBeanClass());
            manager.getReference(bean, bean.getBeanClass(), manager.createCreationalContext(bean)).toString();
        }
        startupBeans.clear();
    }


    private static void initLogging() {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}