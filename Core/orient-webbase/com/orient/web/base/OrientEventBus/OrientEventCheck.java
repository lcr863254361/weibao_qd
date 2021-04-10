/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.web.base.OrientEventBus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mengbin on 16/3/30.
 * Purpose:
 * Detail: 通过注解,可以校验是否是OrientEvent
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OrientEventCheck {

    boolean checked() default true;

}