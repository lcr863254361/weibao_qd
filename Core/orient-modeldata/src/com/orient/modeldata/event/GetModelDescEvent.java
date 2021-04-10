package com.orient.modeldata.event;

import com.orient.modeldata.eventParam.GetModelDescEventParam;
import com.orient.web.base.OrientEventBus.OrientEvent;

/**
 * 获取模型描述
 *
 * @author enjoy
 * @creare 2016-04-01 14:18
 */
public class GetModelDescEvent extends OrientEvent {

    public GetModelDescEvent(Object source, GetModelDescEventParam params) {
        super(source, params);
    }
}
