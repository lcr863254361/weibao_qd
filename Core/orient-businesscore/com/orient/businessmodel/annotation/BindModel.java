package com.orient.businessmodel.annotation;

import java.lang.annotation.*;

/**
 * indicate which ds model the bean class is binded
 *
 * @author Seraph
 *         2016-07-21 下午4:25
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BindModel {

    String modelName();
    String schemaName();
    boolean mapUnderscoreToCamelCase() default true;
}
