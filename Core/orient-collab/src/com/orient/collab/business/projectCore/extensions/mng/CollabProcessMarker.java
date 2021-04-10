package com.orient.collab.business.projectCore.extensions.mng;

import com.orient.collab.business.projectCore.constant.ProcessType;

import java.lang.annotation.*;

/**
 * marker the applied process type
 *
 * @author Seraph
 *         2016-08-12 下午2:28
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CollabProcessMarker {

    /**
     * if empty, then applied to all models
     * @return
     */
    String[] models() default {};

    /**
     * if empty, then applied to all process types
     * @return
     */
    ProcessType[] processType() default {};

    int order() default 1;

}
