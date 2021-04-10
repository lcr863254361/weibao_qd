package com.aptx.utils.event;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface AptxListener {
    Class<? extends AptxEventBus>[] eventBuses() default {DefaultEventBus.class};
    int order() default 0;
}
