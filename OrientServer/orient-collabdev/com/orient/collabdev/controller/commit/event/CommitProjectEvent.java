package com.orient.collabdev.controller.commit.event;

import com.orient.web.base.OrientEventBus.OrientEvent;

/**
 * @Description
 * @Author GNY
 * @Date 2018/9/20 15:53
 * @Version 1.0
 **/
public class CommitProjectEvent extends OrientEvent {

    public CommitProjectEvent(Object source, CommitProjectParam params) {
        super(source, params);
    }

}
