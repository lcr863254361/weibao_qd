package com.orient.pvm.validate.annotation;

import java.lang.annotation.*;

/**
 * 定义验证器的顺序
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidatorOrder{
    int order();
}
