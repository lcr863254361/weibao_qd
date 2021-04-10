package com.orient.modeldata.event;

import com.orient.modeldata.eventParam.GetModelDescEventParam;

/**
 * 获取模型描述
 *
 * @author enjoy
 * @creare 2016-04-01 14:18
 */
public class GetGridModelDescEvent extends GetModelDescEvent {

    public GetGridModelDescEvent(Object source, GetModelDescEventParam params) {
        super(source, params);
    }
}
