package com.orient.collabdev.controller.commit.event;

import com.orient.web.base.OrientEventBus.OrientEvent;

/**
 * @Description 提交计划event
 * @Author GNY
 * @Date 2018/9/25 9:10
 * @Version 1.0
 **/
public class CommitPlanEvent extends OrientEvent {

    public CommitPlanEvent(Object source, CommitPlanParam params) {
        super(source, params);
    }

}
