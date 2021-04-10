package com.orient.background.eventListener;

import com.orient.background.business.ModelFormViewBusiness;
import com.orient.background.event.GenTemplateEvent;
import com.orient.background.eventParam.GenTemplateEventParam;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.sysmodel.domain.form.FreemarkTemplateEntity;
import com.orient.sysmodel.service.form.IFreemarkTemplateService;
import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.form.engine.FreemarkEngine;
import com.orient.web.modelDesc.column.ColumnDesc;
import com.orient.web.modelDesc.model.OrientModelDesc;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by enjoy on 2016/3/21 0021.
 */
@Component
public class GenTemplateListener extends OrientEventListener {

    @Autowired
    ModelFormViewBusiness modelFormViewBusiness;

    @Autowired
    IFreemarkTemplateService freemarkTemplateService;

    @Autowired
    @Qualifier("BusinessModelService")
    protected IBusinessModelService businessModelService;

    @Autowired
    FreemarkEngine freemarkEngine;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }

        //该监听器能够处理对应事件及其子类事件
        return GenTemplateEvent.class == eventType || GenTemplateEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (this.isAbord(applicationEvent)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) applicationEvent;
        GenTemplateEventParam eventSource = (GenTemplateEventParam) orientEvent.getParams();
        Long bindModelID = eventSource.getModelId();
        Long bindTemplateID = eventSource.getTemplateId();
        //获取字段描述
        List<ColumnDesc> columnDescs = modelFormViewBusiness.getFormItems(bindModelID.toString());
        //获取模型描述
        IBusinessModel businessModel = businessModelService.getBusinessModelById(bindModelID.toString(), EnumInter.BusinessModelEnum.Table);
        OrientModelDesc tableDesc = new OrientModelDesc();
        tableDesc.setText(businessModel.getDisplay_name());
        tableDesc.setDbName(businessModel.getS_table_name());
        Map<String, Object> fieldsMap = new HashMap<>();
        fieldsMap.put("table", tableDesc);
        fieldsMap.put("fields", columnDescs);
        //获取模板信息
        FreemarkTemplateEntity templateEntity = freemarkTemplateService.getById(bindTemplateID);
        String macroAlias = templateEntity.getMacroAlias();
        //获取宏模板信息
        String macroHtml = "";
        if (!StringUtil.isEmpty(macroAlias)) {
            FreemarkTemplateEntity macroTemplateEnty = freemarkTemplateService.getByProperties("alias", macroAlias);
            macroHtml = macroTemplateEnty.getHtml();
        }
        try {
            String result = freemarkEngine.parseByStringTemplate(fieldsMap,
                    macroHtml + templateEntity.getHtml());
            eventSource.setHtml(result);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
