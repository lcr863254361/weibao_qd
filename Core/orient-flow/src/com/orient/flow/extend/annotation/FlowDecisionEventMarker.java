package com.orient.flow.extend.annotation;

import com.orient.flow.config.FlowType;

import java.lang.annotation.*;

/**
 * mark the applied range
 * @author Seraph
 * 2016-06-27 上午11:04
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FlowDecisionEventMarker {

    /**
     * if empty, then applied to all flow
     * @return
     */
    String flow() default "";

    /**
     * if empty, then applied to all tasks minus tasks in exceptTasks
     * @return
     */
    String[] decisions() default {};

    String[] exceptDecisions() default {};

    FlowType[] flowTypes() default {};

    int order() default 1;
}