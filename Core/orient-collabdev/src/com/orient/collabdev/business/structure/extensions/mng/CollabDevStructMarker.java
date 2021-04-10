package com.orient.collabdev.business.structure.extensions.mng;

import com.orient.collabdev.business.structure.constant.StructOperateType;
import com.orient.collabdev.constant.ManagerStatusEnum;

import java.lang.annotation.*;

/**
 * marker the applied process type
 *
 * @author Seraph
 * 2016-08-12 下午2:28
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CollabDevStructMarker {

    /**
     * if empty, then applied to all collab models
     *
     * @return
     */
    String[] models() default {};

    /**
     * if empty, then applied to all struct operate type
     *
     * @return
     */
    StructOperateType[] structOperateType() default {};


    ManagerStatusEnum[] projectStatus() default {};

    int order() default 1;

}
