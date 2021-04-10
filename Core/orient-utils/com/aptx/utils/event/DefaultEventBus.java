package com.aptx.utils.event;

import com.google.common.eventbus.SubscriberExceptionHandler;
import org.springframework.stereotype.Component;

@Component
public class DefaultEventBus extends AptxEventBus {
    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public String getIdentifier() {
        return null;
    }

    @Override
    public Class<? extends SubscriberExceptionHandler> getExceptionHandlerClass() {
        return DefaultExceptionHandler.class;
    }

    @Override
    public Class getDeadEventListenerClass() {
        return DefaultDeadEventListener.class;
    }
}
