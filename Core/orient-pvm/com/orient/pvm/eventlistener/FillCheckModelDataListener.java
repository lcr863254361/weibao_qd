package com.orient.pvm.eventlistener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.pvm.bean.CheckTemplateInfo;
import com.orient.pvm.business.CheckModelBusiness;
import com.orient.pvm.business.CheckModelDataTemplateBusiness;
import com.orient.pvm.event.TaskBindCheckModelEvent;
import com.orient.pvm.eventparam.TaskBindCheckModelEventParam;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.pvm.TaskCheckModel;
import com.orient.sysmodel.domain.pvm.TaskCheckRelation;
import com.orient.sysmodel.service.pvm.impl.TaskCheckModelService;
import com.orient.sysmodel.service.pvm.impl.TaskCheckRelationService;
import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventCheck;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mengbin on 16/7/30.
 * Purpose:  读去XML,为checkModel添加数据,并且设置与task的关系表
 * Detail:
 */
@Service
public class FillCheckModelDataListener extends OrientEventListener {

    @Autowired
    private CheckModelDataTemplateBusiness checkModelDataTemplateBusiness;

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    protected IBusinessModelService businessModelService;

    @Autowired
    private TaskCheckRelationService taskCheckRelationService;

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
        CheckTemplateInfo checkTemplateInfo = checkModelDataTemplateBusiness.getCheckTemplateInfo(param.getCheckModelDataTemplate());
        if (null != checkTemplateInfo) {
            fillDate(checkTemplateInfo, param);
        }
    }

    private boolean fillDate(CheckTemplateInfo checkTemplateInfo, TaskBindCheckModelEventParam param) {

        IBusinessModel checkModel = businessModelService.getBusinessModelById(param.checkModelId, EnumInter.BusinessModelEnum.Table);
        for (Map<String, String> row : checkTemplateInfo.getDatas()) {
            //转化显示名 到 字段真实名
            Map<String, String> realDataMap = transformDisplayMapToSMap(row, checkModel);
            String dataId = orientSqlEngine.getBmService().insertModelData(checkModel, realDataMap);
            TaskCheckRelation taskCheckRelation = new TaskCheckRelation();
            taskCheckRelation.setCheckdataid(Long.valueOf(dataId));
            taskCheckRelation.setCheckmodelid(Long.valueOf(param.checkModelId));
            taskCheckRelation.setNodeId(param.nodeId);
            taskCheckRelationService.save(taskCheckRelation);

        }
        TaskCheckModel taskCheckModel = (TaskCheckModel) param.getFlowParams("taskcheckmodel");
        taskCheckModel.setSignroles(checkTemplateInfo.getSignroles());
        taskCheckModel.setRemark(checkTemplateInfo.getRemark());
        return true;
    }

    private Map<String, String> transformDisplayMapToSMap(Map<String, String> row, IBusinessModel model) {
        //删除默认ID属性
        Map<String, String> retVal = new HashMap<>();
        Map<String, String> columnMapping = new HashMap<>();
        model.getAllBcCols().forEach(iBusinessColumn -> {
            columnMapping.put(iBusinessColumn.getDisplay_name(), iBusinessColumn.getS_column_name());
        });
        row.forEach((key, value) -> {
            String scolumnName = columnMapping.get(key);
            if (!StringUtil.isEmpty(scolumnName)) {
                retVal.put(scolumnName, value);
            }
        });
        return retVal;
    }

}
