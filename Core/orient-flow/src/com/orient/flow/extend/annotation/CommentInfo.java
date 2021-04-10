package com.orient.flow.extend.annotation;

import java.lang.annotation.*;

/**
 * a comment information
 *
 * @author Seraph
 * 2016-06-28 上午8:51
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommentInfo {

    String displayName();

    String description() default "";

    boolean allowSelect() default true;
}
