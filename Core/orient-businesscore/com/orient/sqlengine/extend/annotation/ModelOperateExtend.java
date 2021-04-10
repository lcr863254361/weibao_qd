package com.orient.sqlengine.extend.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by qjs on 2017/2/11.
 * 注解实现TbomIcon接口的实现类，说明实现类返回iconCLs针对的tbom
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ModelOperateExtend {
    //modelSName T_TEST
    //* 表示所有模型
    String[] modelNames();

    //displayname：数据模型
    String schemaName();

    //排序
    int order() default 0;
}
