package com.orient.collabdev.business.common.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 节点关联信息操作
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface NodeRefOperate {

    //状态标志
    int order() default -1;

}
