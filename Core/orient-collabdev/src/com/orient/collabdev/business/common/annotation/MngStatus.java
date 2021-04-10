package com.orient.collabdev.business.common.annotation;

import com.orient.collabdev.constant.ManagerStatusEnum;

import java.lang.annotation.*;

/**
 * 管理状态
 *
 * @author panduanduan
 * @create 2018-07-28 2:21 PM
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MngStatus {

    //状态标志
    ManagerStatusEnum status();
}
