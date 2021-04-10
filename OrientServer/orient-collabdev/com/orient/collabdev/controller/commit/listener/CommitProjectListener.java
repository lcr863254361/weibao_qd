package com.orient.collabdev.controller.commit.listener;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collabdev.business.processing.DevDataCopyBusiness;
import com.orient.collabdev.business.startup.SonPlanBusiness;
import com.orient.collabdev.business.version.DefaultCollabVersionMng;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.collabdev.controller.commit.event.CommitProjectEvent;
import com.orient.collabdev.controller.commit.event.CommitProjectParam;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.utils.CommonTools;
import com.orient.utils.exception.OrientBaseAjaxException;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.collabdev.constant.CollabDevConstants.NODE_TYPE_PLAN;
import static com.orient.collabdev.constant.CollabDevConstants.NODE_TYPE_PRJ;
import static com.orient.collabdev.constant.CollabDevConstants.PROJECT;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;


/**
 * @Description
 * @Author GNY
 * @Date 2018/9/20 15:57
 * @Version 1.0
 **/
@Component
public class CommitProjectListener extends OrientEventListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == CommitProjectEvent.class || CommitProjectEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        OrientEvent orientEvent = (OrientEvent) applicationEvent;
        CommitProjectParam eventParam = (CommitProjectParam) orientEvent.getParams();
        String projectNodeId = eventParam.getProjectNodeId();
        CollabNode projectNode = collabNodeService.getById(projectNodeId);
        //进行中的项目才能提交
        if (projectNode.getStatus().equals(ManagerStatusEnum.PROCESSING.toString())) {
            //获取项目节点下的所有计划节点,这里不需要判断子计划状态，子计划在计划提交中判断
            List<CollabNode> planNodeList = collabNodeService.list(Restrictions.eq("pid", projectNodeId), Restrictions.eq("type", NODE_TYPE_PLAN));
            planNodeList.forEach(planNode -> {
                String status = planNode.getStatus();
                if (status.equals(ManagerStatusEnum.PROCESSING.toString()) || status.equals(ManagerStatusEnum.UNSTART.toString())) {
                    throw new OrientBaseAjaxException("", "项目下存在没有完成的计划，无法提交！");
                }
            });
            //修改项目状态并设置完成日期,升级项目版本和研发数据版本
            modifyStatusAndIncreaseVersion(projectNodeId);
        } else {
            throw new OrientBaseAjaxException("", "项目未启动或已经结束，无法提交！");
        }
    }

    private void modifyStatusAndIncreaseVersion(String projectNodeId) {
        //获取现在的节点版本号
        CollabNode oldProjectNode = collabNodeService.getById(projectNodeId);
        //修改CB_PROJECT_240中项目的状态为完成，设置项目实际完成时间，注意修改时会走版本升级的切面，不需要手动提升版本
        IBusinessModel projectBM = businessModelService.getBusinessModelBySName(PROJECT, COLLAB_SCHEMA_ID, Table);
        projectBM.setReserve_filter("AND ID = '" + oldProjectNode.getBmDataId() + "'");
        List<Map> projectMapList = orientSqlEngine.getBmService().createModelQuery(projectBM).list();
        if (!CommonTools.isEmptyList(projectMapList)) {
            Map projectMap = projectMapList.get(0);
            String id = CommonTools.Obj2String(projectMap.get("id"));
            projectMap.put("STATUS_" + projectBM.getId(), ManagerStatusEnum.DONE.toString());
            projectMap.put("ACTUAL_END_DATE_" + projectBM.getId(), CommonTools.FormatDate(new Date(), "yyyy-MM-dd"));
            orientSqlEngine.getBmService().updateModelData(projectBM, projectMap, id);
        }
    }

    @Autowired
    SonPlanBusiness sonPlanBusiness;

    @Autowired
    IBusinessModelService businessModelService;

    @Autowired
    ISqlEngine orientSqlEngine;

    @Autowired
    ICollabNodeService collabNodeService;

    @Autowired
    DefaultCollabVersionMng defaultCollabVersionMng;

    @Autowired
    DevDataCopyBusiness devDataCopyBusiness;

}
