package com.orient.history.core.util;

import com.orient.config.ConfigInfo;


/**
 * constants in collab module
 *
 * @author Seraph
 *         2016-07-06 下午1:52
 */
public class HisTaskTypeConstants extends ConfigInfo {

    public static final String AUDIT_TASK;

    public static final String COLLAB_TASK;

    public static final String DATA_TASK;

    public static final String PLAN_TASK;

    static {
        AUDIT_TASK = getPropertyValueConfigured("TASKTYPE.AUDIT_TASK", "config.properties", "");
        COLLAB_TASK = getPropertyValueConfigured("TASKTYPE.COLLAB_TASK", "config.properties", "");
        DATA_TASK = getPropertyValueConfigured("TASKTYPE.DATA_TASK", "config.properties", "");
        PLAN_TASK = getPropertyValueConfigured("TASKTYPE.PLAN_TASK", "config.properties", "");
    }
}
