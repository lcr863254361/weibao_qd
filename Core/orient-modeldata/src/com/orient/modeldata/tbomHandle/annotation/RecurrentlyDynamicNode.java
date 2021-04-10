package com.orient.modeldata.tbomHandle.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * TBom动态节点递归定制注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RecurrentlyDynamicNode {
    //模型名称
    String modelName();

    //校验的顺序
    int order();

}
