package fr.pagesjaunes.socletechnique.promethee;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.Ordered;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.NonNull;

public class PrometheeResourceLoadBeanPostProcessor implements BeanPostProcessor, BeanFactoryPostProcessor, ApplicationContextAware, ResourceLoaderAware, Ordered {

    private ApplicationContext applicationContext;

    private ResourceLoader resourceLoader;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setResourceLoader(@NonNull ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean,@NonNull String beanName) throws BeansException { // NOSONAR 'throws BeansException' is part of the parent method signature
        if (bean instanceof ResourceLoaderAware resourceLoaderAware) {
            resourceLoaderAware.setResourceLoader(resourceLoader);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean,@NonNull String beanName) throws BeansException { // NOSONAR 'throws BeansException' is part of the parent method signature
        return bean;
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException { // NOSONAR 'throws BeansException' is part of the parent method signature
        resourceLoader = new PrometheeResourceLoader(applicationContext, resourceLoader);
        beanFactory.registerResolvableDependency(ResourceLoader.class, resourceLoader);
    }
}
