package com.aptx.utils.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class DefaultExceptionHandler implements SubscriberExceptionHandler {
    public static final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @Override
    public void handleException(Throwable exception, SubscriberExceptionContext context) {
        Object event = context.getEvent();
        EventBus eventBus = context.getEventBus();
        Object subscriber = context.getSubscriber();
        Method subscriberMethod = context.getSubscriberMethod();
        logger.error("AptxEventListener throws an exception: " +
                "eventBus="+eventBus.getClass().getName()+", " +
                "event="+event.getClass().getName()+", " +
                "subscriber="+subscriber.getClass().getName()+", " +
                "subscriberMethod="+subscriberMethod.getName(), exception);
    }
}
