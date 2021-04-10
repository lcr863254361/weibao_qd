package com.orient.background.event;

import com.orient.background.eventParam.GenTemplateEventParam;
import com.orient.web.base.OrientEventBus.OrientEvent;

/**
 * Created by enjoy on 2016/3/21 0021.
 * 根据模板ID 以及 模型ID 通过freeMarker生成前段html
 */
public class GenTemplateEvent extends OrientEvent {

    public GenTemplateEvent(Object source,GenTemplateEventParam param){
        super(source,param);
    }
}
