package com.orient.pvm.eventlistener;

import com.orient.pvm.business.CheckModelBusiness;
import com.orient.pvm.event.TaskBindCheckModelEvent;
import com.orient.pvm.eventparam.TaskBindCheckModelEventParam;
import com.orient.sysmodel.domain.pvm.TaskCheckModel;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventCheck;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

/**
 * Created by mengbin on 16/7/30.
 * Purpose:
 * Detail:
 */
@Service
public class CreateTaskCheckModelListener extends OrientEventListener {


    @Autowired
    CheckModelBusiness checkModelBusiness;

    @Override
    @OrientEventCheck()
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {

        //该监听器能够处理对应事件及其子类事件
        return TaskBindCheckModelEvent.class == aClass || TaskBindCheckModelEvent.class.isAssignableFrom(aClass);

    }


    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (this.isAbord(applicationEvent)) {
            return;
        }
        TaskBindCheckModelEventParam param = (TaskBindCheckModelEventParam) ((OrientEvent) applicationEvent).getParams();
        TaskCheckModel taskCheckModel = checkModelBusiness.bindCheckModel(param.nodeId, param.checkModelId, param.getStatus(),
                param.getHtml(), param.getHtmlName());
        param.setFlowParams("taskcheckmodel", taskCheckModel);
    }
}
