package com.orient.collabdev.controller.startup.listener;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collabdev.business.ancestry.taskanalyze.context.CopyNodeAnalyzeContext;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.collabdev.controller.startup.event.ManualStartUpPlanEvent;
import com.orient.collabdev.controller.startup.event.ManualStartUpPlanParam;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.utils.CommonTools;
import com.orient.utils.exception.OrientBaseAjaxException;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.collabdev.constant.CollabDevConstants.PLAN;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * @Description 手动启动计划
 * @Author GNY
 * @Date 2018/9/27 9:01
 * @Version 1.0
 **/
@Component
public class ManualStartUpPlanListener extends OrientEventListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == ManualStartUpPlanEvent.class || ManualStartUpPlanEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        OrientEvent orientEvent = (OrientEvent) applicationEvent;
        ManualStartUpPlanParam eventParam = (ManualStartUpPlanParam) orientEvent.getParams();
        String planNodeId = eventParam.getPlanNodeId();
        CollabNode planNode = collabNodeService.getById(planNodeId);
        switch (ManagerStatusEnum.fromString(planNode.getStatus())) {
            case DONE:
                throw new OrientBaseAjaxException("", "工作包已经完成，禁止再次启动");
            case UNSTART:
                IBusinessModel planBM = businessModelService.getBusinessModelBySName(PLAN, COLLAB_SCHEMA_ID, Table);
                planBM.setReserve_filter("AND ID = '" + planNode.getBmDataId() + "'");
                List<Map> planMapList = orientSqlEngine.getBmService().createModelQuery(planBM).list();
                if (!CommonTools.isEmptyList(planMapList)) {
                    //设置状态为进行中，设置实际开始时间
                    Map planMap = planMapList.get(0);
                    String id = CommonTools.Obj2String(planMap.get("id"));
                    planMap.put("STATUS_" + planBM.getId(), ManagerStatusEnum.PROCESSING.toString());
                    planMap.put("ACTUAL_START_DATE_" + planBM.getId(), CommonTools.FormatDate(new Date(), "yyyy-MM-dd"));
                    orientSqlEngine.getBmService().updateModelData(planBM, planMap, id);
                }
                break;
            case PROCESSING:
                throw new OrientBaseAjaxException("", "工作包已经启动，无需再次启动");
            default:
                break;
        }
    }

    @Autowired
    ICollabNodeService collabNodeService;

    @Autowired
    IBusinessModelService businessModelService;

    @Autowired
    ISqlEngine orientSqlEngine;

}
