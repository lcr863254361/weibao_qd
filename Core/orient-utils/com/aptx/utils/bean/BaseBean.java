package com.aptx.utils.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringValueResolver;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

public abstract class BaseBean extends BaseObject implements ApplicationContextAware, ApplicationEventPublisherAware,
        ResourceLoaderAware, EnvironmentAware, EmbeddedValueResolverAware, ServletContextAware {
    protected transient ApplicationContext applicationContext;
    protected transient ApplicationEventPublisher eventPublisher;
    protected transient ResourceLoader resourceLoader;
    protected transient Environment environment;
    protected transient StringValueResolver valueResolver;
    protected transient ServletContext servletContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.valueResolver = valueResolver;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
