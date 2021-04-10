package com.orient.collabdev.business.common.annotation;

import com.orient.collabdev.constant.VersionStatusEnum;
import org.springframework.stereotype.Component;

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
@Component
public @interface VersionStatus {

    //状态标志
    VersionStatusEnum status();
}
