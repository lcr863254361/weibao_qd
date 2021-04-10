package com.orient.modeldata.tbomHandle.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 静态节点增加绑定模型注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface StaticNodeModel {
    //使用实现类的tbom的名称
    String tbomName();

    //校验的顺序
    int order();

}
