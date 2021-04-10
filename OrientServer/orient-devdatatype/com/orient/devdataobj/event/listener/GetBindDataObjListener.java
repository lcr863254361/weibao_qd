package com.orient.devdataobj.event.listener;

import com.orient.devdataobj.business.DataObjectBusiness;
import com.orient.devdataobj.event.GetDataObjEvent;
import com.orient.devdataobj.event.param.GetDataObjParam;
import com.orient.pvm.event.ImportCheckTemplateEvent;
import com.orient.pvm.eventparam.ImportCheckTemplateEventParam;
import com.orient.sysmodel.domain.pvm.CheckModelDataTemplate;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventCheck;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by mengbin on 16/8/6.
 * Purpose:
 * Detail:
 */
@Component
public class GetBindDataObjListener extends OrientEventListener {

    @Autowired
    private DataObjectBusiness dataObjectBusiness;

    @Override
    @OrientEventCheck()
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        //该监听器能够处理对应事件及其子类事件
        return GetDataObjEvent.class == aClass || GetDataObjEvent.class.isAssignableFrom(aClass);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (this.isAbord(applicationEvent)) {
            return;
        }
        GetDataObjParam param = (GetDataObjParam) ((OrientEvent) applicationEvent).getParams();
        List<DataObjectEntity> objs = dataObjectBusiness.getAllCurrentDataObject(param.nodeId, param.nodeVersion, param.globalFlag, true);
        param.setFlowParams("DataObjEcntitys", objs);
    }

}
