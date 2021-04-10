package com.orient.modeldata.validateHandler.annotation;

import java.lang.annotation.*;

/**
 * 定义模型校验规则
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateModelData {

    //需要校验的模型名称集合 * 表示适用于所有模型
    //SModelName T_TEST_1001
    String[] modelNames();

    //校验的顺序
    int order();
}
