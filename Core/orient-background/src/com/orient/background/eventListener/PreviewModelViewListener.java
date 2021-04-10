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
        //准备模板
        Integer regenTemplate = eventSource.getReGenTemplate();
        ModelFormViewEntity modelFormViewEntity = eventSource.getFormValue();
        //绑定模型
        Long modelId = modelFormViewEntity.getModelid();
        String dataId = eventSource.getDataId();
        String template = modelFormViewEntity.getTemplate();
        if (1 == regenTemplate) {
            //重新生成freemarker模板
            String html = modelFormViewEntity.getHtml();
            template = ModelViewFormHelper.getFreeMarkerTemplate(modelFormViewEntity.getHtml(), modelId);
        }
        //获取模型描述
        IBusinessModel businessModel = businessModelService.getBusinessModelById(modelId.toString(), EnumInter.BusinessModelEnum.Table);
        //准备数据
        //准备权限数据 TODO 转成Java类
        Map<String, Map<String, String>> permission = preparePermissionData();
        //准备模型数据
        Map<String, Object> dataMap = prepareModelData(businessModel, dataId);
        //生成前端HTML TODO 转成Java类
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
     * 准备模型数据
     *
     * @param businessModel
     * @return
     */
    private Map<String, Object> prepareModelData(IBusinessModel businessModel, String dataId) {
        Map<String, Object> resultMap = new HashMap<>();
        //注入当前用户
        //注入当前部门
        //等
        if (!StringUtil.isEmpty(dataId)) {
            businessModel.appendCustomerFilter(new CustomerFilter("ID", EnumInter.SqlOperation.Equal, dataId));
            List<Map> dataList = orientSqlEngine.getBmService().createModelQuery(businessModel).list();
            //转化为真实值
            businessModelService.dataChangeModel(orientSqlEngine, businessModel, dataList, false);
            if (!dataList.isEmpty()) {
                resultMap = dataList.get(0);
            }
        }
        return resultMap;
    }

    /**
     * 模型权限描述
     *
     * @return
     */
    private Map<String, Map<String, String>> preparePermissionData() {
        Map<String, Map<String, String>> permission = new HashMap<>();

        //字段权限控制
        Map<String, String> fieldPermission = new HashMap<>();
        //模型权限控制
        Map<String, String> tablePermission = new HashMap<>();
        permission.put("field", fieldPermission);
        permission.put("table", tablePermission);
        return permission;
    }


}
