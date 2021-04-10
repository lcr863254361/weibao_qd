package com.orient.history.core.engine.prepare.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * a comment information
 *
 * @author Seraph
 *         2016-06-28 上午8:51
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface PrepareIntermediator {

    String[] types();

    int order() default 0;
}
