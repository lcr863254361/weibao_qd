package com.orient.background.eventListener;

import com.orient.background.business.FormControlBusiness;
import com.orient.background.event.PreviewModelViewEvent;
import com.orient.background.eventParam.PreviewModelViewEventParam;
import com.orient.background.util.ModelViewFormHelper;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.form.ModelFormViewEntity;
import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.form.engine.FreemarkEngine;
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
 * Created by enjoy on 2016/3/23 0023.
 */
@Component
public class PreviewModelViewListener extends OrientEventListener {

    @Autowired
    @Qualifier("BusinessModelService")
    protected IBusinessModelService businessModelService;

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    FreemarkEngine freemarkEngine;

    @Autowired
    FormControlBusiness formControlBusiness;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == PreviewModelViewEvent.class || PreviewModelViewEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (this.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) event;
        PreviewModelViewEventParam eventSource = (PreviewModelViewEventParam) orientEvent.getParams();
        //????????????
        Integer regenTemplate = eventSource.getReGenTemplate();
        ModelFormViewEntity modelFormViewEntity = eventSource.getFormValue();
        //????????????
        Long modelId = modelFormViewEntity.getModelid();
        String dataId = eventSource.getDataId();
        String template = modelFormViewEntity.getTemplate();
        if (1 == regenTemplate) {
            //????????????freemarker??????
            String html = modelFormViewEntity.getHtml();
            template = ModelViewFormHelper.getFreeMarkerTemplate(modelFormViewEntity.getHtml(), modelId);
        }
        //??????????????????
        IBusinessModel businessModel = businessModelService.getBusinessModelById(modelId.toString(), EnumInter.BusinessModelEnum.Table);
        //????????????
        //?????????????????? TODO ??????Java???
        Map<String, Map<String, String>> permission = preparePermissionData();
        //??????????????????
        Map<String, Object> dataMap = prepareModelData(businessModel, dataId);
        //????????????HTML TODO ??????Java???
        Map<String, Map> model = new HashMap<>();
        model.put("main", dataMap);
        Map<String, Object> map = new HashMap<>();
        map.put("model", model);
        map.put("permission", permission);
        map.put("service", formControlBusiness);
        try {
            String output = freemarkEngine.parseByStringTemplate(map, template);
            eventSource.setOutHtml(output);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * ??????????????????
     *
     * @param businessModel
     * @return
     */
    private Map<String, Object> prepareModelData(IBusinessModel businessModel, String dataId) {
        Map<String, Object> resultMap = new HashMap<>();
        //??????????????????
        //??????????????????
        //???
        if (!StringUtil.isEmpty(dataId)) {
            businessModel.appendCustomerFilter(new CustomerFilter("ID", EnumInter.SqlOperation.Equal, dataId));
            List<Map> dataList = orientSqlEngine.getBmService().createModelQuery(businessModel).list();
            //??????????????????
            businessModelService.dataChangeModel(orientSqlEngine, businessModel, dataList, false);
            if (!dataList.isEmpty()) {
                resultMap = dataList.get(0);
            }
        }
        return resultMap;
    }

    /**
     * ??????????????????
     *
     * @return
     */
    private Map<String, Map<String, String>> preparePermissionData() {
        Map<String, Map<String, String>> permission = new HashMap<>();

        //??????????????????
        Map<String, String> fieldPermission = new HashMap<>();
        //??????????????????
        Map<String, String> tablePermission = new HashMap<>();
        permission.put("field", fieldPermission);
        permission.put("table", tablePermission);
        return permission;
    }


}
