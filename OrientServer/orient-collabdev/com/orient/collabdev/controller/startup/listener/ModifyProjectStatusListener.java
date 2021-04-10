package com.orient.collabdev.controller.startup.listener;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.collabdev.controller.startup.event.StartUpProjectEvent;
import com.orient.collabdev.controller.startup.event.StartUpProjectParam;
import com.orient.msg.bussiness.MsgBussiness;
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
import static com.orient.collabdev.constant.CollabDevConstants.PROJECT;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * @Description 项目启动时修改项目的状态和实际开始时间的处理类
 * @Author GNY
 * @Date 2018/9/17 14:07
 * @Version 1.0
 **/
@Component
public class ModifyProjectStatusListener extends OrientEventListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == StartUpProjectEvent.class || StartUpProjectEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        OrientEvent orientEvent = (OrientEvent) applicationEvent;
        StartUpProjectParam eventParam = (StartUpProjectParam) orientEvent.getParams();
        String projectNodeId = eventParam.getProjectNodeId();
        //在CB_PROJECT_240表中修改项目状态和实际开始时间字段，切面会修改CB_SYS_NODE表中的记录
        CollabNode projectNode = collabNodeService.getById(projectNodeId);
        if (ManagerStatusEnum.UNSTART.toString().equals(projectNode.getStatus())) {
            IBusinessModel projectBM = businessModelService.getBusinessModelBySName(PROJECT, COLLAB_SCHEMA_ID, Table);
            projectBM.setReserve_filter("AND ID = '" + projectNode.getBmDataId() + "'");
            List<Map> projectMapList = orientSqlEngine.getBmService().createModelQuery(projectBM).list();
            if (projectMapList.size() > 0) {
                Map projectMap = projectMapList.get(0);
                String id = CommonTools.Obj2String(projectMap.get("id"));
                projectMap.put("STATUS_" + projectBM.getId(), ManagerStatusEnum.PROCESSING.toString());
                projectMap.put("ACTUAL_START_DATE_" + projectBM.getId(), CommonTools.FormatDate(new Date(), "yyyy-MM-dd"));
                orientSqlEngine.getBmService().updateModelData(projectBM, projectMap, id);
            }
        } else {
            throw new OrientBaseAjaxException("", "项目已经启动或完成，无法再次启动");
        }
    }

    @Autowired
    ICollabNodeService collabNodeService;

    @Autowired
    IBusinessModelService businessModelService;

    @Autowired
    ISqlEngine orientSqlEngine;

}
