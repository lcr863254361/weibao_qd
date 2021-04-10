package com.orient.background.eventListener;

import com.orient.background.event.SaveModelViewEvent;
import com.orient.background.eventParam.SaveModelViewEventParam;
import com.orient.background.util.ModelViewFormHelper;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.sysmodel.domain.form.ModelFormViewEntity;
import com.orient.sysmodel.service.form.IFreemarkTemplateService;
import com.orient.sysmodel.service.form.IModelFormViewService;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.form.engine.FreemarkEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * Created by enjoy on 2016/3/22 0022.
 * 保存自定义模型表单
 */
@Component
public class SaveModelViewListener extends OrientEventListener {

    @Autowired
    IModelFormViewService modelFormViewService;

    @Autowired
    IFreemarkTemplateService freemarkTemplateService;

    @Autowired
    @Qualifier("BusinessModelService")
    protected IBusinessModelService businessModelService;

    @Autowired
    FreemarkEngine freemarkEngine;


    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if(!isOrientEvent(eventType)){
            return false;
        }
        return eventType == SaveModelViewEvent.class||SaveModelViewEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (this.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent)event;
        SaveModelViewEventParam eventSource = (SaveModelViewEventParam) orientEvent.getParams();
        //绑定模板
        ModelFormViewEntity modelFormViewEntity = eventSource.getModelFormViewEntity();
        Long modelId = modelFormViewEntity.getModelid();
        //生成freemarker模板
        String template = ModelViewFormHelper.getFreeMarkerTemplate(modelFormViewEntity.getHtml(), modelId);
        modelFormViewEntity.setTemplate(template);
        //TODO 绑定创建人
        if (null != modelFormViewEntity.getId()) {
            modelFormViewService.update(modelFormViewEntity);
        } else {
            modelFormViewEntity.setCreatetime(new Timestamp(System.currentTimeMillis()));
            modelFormViewService.save(modelFormViewEntity);
        }

    }



}
