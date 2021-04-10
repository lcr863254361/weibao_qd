package com.orient.modeldata.event;

import com.orient.modeldata.eventParam.GetTbomNodesEventParam;
import com.orient.web.base.OrientEventBus.OrientEvent;

/**
 * 获取模型描述
 *
 * @author enjoy
 * @creare 2016-04-01 14:18
 */
public class GetTbomNodesEvent extends OrientEvent {

    public GetTbomNodesEvent(Object source, GetTbomNodesEventParam params) {
        super(source, params);
    }
}
