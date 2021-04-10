package com.orient.history.core;

import com.orient.history.core.binddata.model.HisTaskInfo;

/**
 * Created by Administrator on 2016/9/26 0026.
 * 历史任务引擎
 */
public interface IHisTaskEngine {

    HisTaskInfo saveHisTaskInfo(String taskId);
}
