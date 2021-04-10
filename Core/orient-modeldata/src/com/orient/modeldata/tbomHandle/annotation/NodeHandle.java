package com.orient.modeldata.tbomHandle.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 对返回的树节点进行处理
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface NodeHandle {
    //校验的顺序
    int order();

    String tbomName();
}
