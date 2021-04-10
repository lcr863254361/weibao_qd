package com.aptx.utils.event;

import com.aptx.utils.CollectionUtil;
import com.aptx.utils.bean.BaseObject;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public abstract class AptxEventBus extends BaseObject implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor, InitializingBean {
    protected static final Executor asyncExecutor;
    static {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("AptxEventAsync-");
        executor.initialize();
        asyncExecutor = executor;
    }

    protected ApplicationContext applicationContext;

    protected EventBus eventBus = null;
    protected Map<Class, Object> registeredListeners = Maps.newConcurrentMap();

    public abstract boolean isAsync();

    public abstract String getIdentifier();

    public abstract Class<? extends SubscriberExceptionHandler> getExceptionHandlerClass();

    public abstract Class getDeadEventListenerClass();

    public AptxEventBus() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(isAsync()) {
            initAsyncEventBus();
        }
        else {
            initEventBus();
        }

        registerListeners();

        registerDeadEventListener();
    }

    protected void initAsyncEventBus() {
        Class<? extends SubscriberExceptionHandler> exceptionHandlerClass = getExceptionHandlerClass();
        if(exceptionHandlerClass != null) {
            SubscriberExceptionHandler exceptionHandler = this.applicationContext.getBean(exceptionHandlerClass);
            if(exceptionHandler == null) {
                throw new RuntimeException("Init aptx async event bus error: bean of type [" + exceptionHandlerClass.getName() + "] not found!");
            }
            logger.info("Init aptx async event bus with exception handler class: "+exceptionHandlerClass.getName());
            this.eventBus = new AsyncEventBus(asyncExecutor, exceptionHandler);
        }
        else {
            String identifier = getIdentifier();
            if(Strings.isNullOrEmpty(identifier)) {
                logger.info("Init aptx async event bus with identifier: "+identifier);
                this.eventBus = new AsyncEventBus(identifier, asyncExecutor);
            }
            else {
                logger.info("Init default aptx async event bus");
                this.eventBus = new AsyncEventBus(asyncExecutor);
            }
        }
    }

    protected void initEventBus() {
        Class<? extends SubscriberExceptionHandler> exceptionHandlerClass = getExceptionHandlerClass();
        if(exceptionHandlerClass != null) {
            SubscriberExceptionHandler exceptionHandler = this.applicationContext.getBean(exceptionHandlerClass);
            if(exceptionHandler == null) {
                throw new RuntimeException("Init aptx event bus error: bean of type [" + exceptionHandlerClass.getName() + "] not found!");
            }
            logger.info("Init aptx event bus with exception handler class: "+exceptionHandlerClass.getName());
            this.eventBus = new EventBus(exceptionHandler);
        }
        else {
            String identifier = getIdentifier();
            if(Strings.isNullOrEmpty(identifier)) {
                logger.info("Init aptx event bus with identifier: "+identifier);
                this.eventBus = new EventBus(identifier);
            }
            else {
                logger.info("Init default aptx event bus");
                this.eventBus = new EventBus();
            }
        }
    }

    protected void registerListeners() {
        Collection<Object> listeners = this.applicationContext.getBeansWithAnnotation(AptxListener.class).values();
        List<Object> matchListeners = Lists.newArrayList();
        for(Object listener : listeners) {
            AptxListener anno = listener.getClass().getAnnotation(AptxListener.class);
            Class<? extends AptxEventBus>[] eventBuses = anno.eventBuses();
            if(ArrayUtils.contains(eventBuses, this.getClass())) {
                matchListeners.add(listener);
            }
        }

        Ordering<Object> ordering = CollectionUtil.getNaturalOrdering(true, new Function<Object, Comparable>() {
            @Override
            public Comparable apply(Object input) {
                AptxListener anno = input.getClass().getAnnotation(AptxListener.class);
                return anno.order();
            }
        });
        Collections.sort(matchListeners, ordering);
        for(Object matchListener : matchListeners) {
            if(this.register(matchListener)) {
                logger.debug("Registered aptx listener of type: " + matchListener.getClass().getName());
            }
        }
    }

    protected void registerDeadEventListener() {
        Class deadEventListenerClass = getDeadEventListenerClass();
        if(deadEventListenerClass != null) {
            Object deadEventListener = this.applicationContext.getBean(deadEventListenerClass);
            if(deadEventListener == null) {
                deadEventListener = this.applicationContext.getBean(DefaultDeadEventListener.class);
            }
            if(deadEventListener != null) {
                if(this.register(deadEventListener)) {
                    logger.info("Registered aptx dead event listener of type: "+deadEventListener.getClass().getName());
                }
            }
        }
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    public boolean register(Object obj) {
        if(obj == null) {
            return false;
        }
        else if(registeredListeners.get(obj.getClass()) != null) {
            return false;
        }
        else {
            this.eventBus.register(obj);
            registeredListeners.put(obj.getClass(), obj);
            return true;
        }
    }

    public Object register(Class clazz) {
        Object obj = this.applicationContext.getBean(clazz);
        if(this.register(obj)) {
            return obj;
        }
        else {
            return null;
        }
    }

    public boolean unregister(Object obj) {
        if(obj == null) {
            return false;
        }
        else if(registeredListeners.get(obj.getClass()) == null) {
            return false;
        }
        else {
            this.eventBus.unregister(obj);
            registeredListeners.remove(obj.getClass());
            return true;
        }
    }

    public Object unregister(Class clazz) {
        Object obj = registeredListeners.get(clazz);
        if(this.unregister(obj)) {
            return obj;
        }
        else {
            return null;
        }
    }

    public void post(Object event) {
        this.eventBus.post(event);
    }

    @Override
    public String toString() {
        return this.eventBus.toString();
    }
}
