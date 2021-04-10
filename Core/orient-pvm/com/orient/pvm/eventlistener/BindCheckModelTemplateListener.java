package com.orient.pvm.eventlistener;

import com.orient.pvm.event.ImportCheckTemplateEvent;
import com.orient.pvm.eventparam.ImportCheckTemplateEventParam;
import com.orient.sysmodel.domain.pvm.CheckModelDataTemplate;
import com.orient.sysmodel.service.pvm.ICheckModelDataTemplateService;
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
public class BindCheckModelTemplateListener extends OrientEventListener {

    @Autowired

    private ICheckModelDataTemplateService checkModelDataTemplateService;


    @Override
    @OrientEventCheck()
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {

        //该监听器能够处理对应事件及其子类事件
        return ImportCheckTemplateEvent.class == aClass || ImportCheckTemplateEvent.class.isAssignableFrom(aClass);

    }


    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (this.isAbord(applicationEvent)) {
            return;
        }
        ImportCheckTemplateEventParam param = (ImportCheckTemplateEventParam) ((OrientEvent) applicationEvent).getParams();
        CheckModelDataTemplate checkModelDataTemplate = param.getCheckModelDataTemplate();
        if (checkModelDataTemplate.getId() != null) {
            //更新操作
            checkModelDataTemplateService.update(checkModelDataTemplate);
        } else {
            //修改操作
            checkModelDataTemplateService.save(checkModelDataTemplate);
        }
    }

}
