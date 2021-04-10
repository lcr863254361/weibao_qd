package com.orient.collabdev.controller.commit.listener;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collabdev.business.processing.DevDataCopyBusiness;
import com.orient.collabdev.business.startup.PlanStartBusiness;
import com.orient.collabdev.business.structure.StructureBusiness;
import com.orient.collabdev.business.version.DefaultCollabVersionMng;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.collabdev.controller.commit.event.CommitPlanEvent;
import com.orient.collabdev.controller.commit.event.CommitPlanParam;
import com.orient.collabdev.model.CollabDevNodeDTO;
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
import static com.orient.collabdev.constant.CollabDevConstants.*;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * @Description
 * @Author GNY
 * @Date 2018/9/25 9:11
 * @Version 1.0
 **/
@Component
public class CommitPlanListener extends OrientEventListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == CommitPlanEvent.class || CommitPlanEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        OrientEvent orientEvent = (OrientEvent) applicationEvent;
        CommitPlanParam eventParam = (CommitPlanParam) orientEvent.getParams();
        String planNodeId = eventParam.getPlanNodeId();
        CollabNode planNode = collabNodeService.getById(planNodeId);
        //进行中的计划才能提交
        if (planNode.getStatus().equals(ManagerStatusEnum.PROCESSING.toString())) {
            //获取计划下所有的任务和子计划，判断任务和子计划的状态是否都是完成，如果全部完成则可以提交计划，否则不能提交计划
            List<CollabNode> taskNodeList = collabNodeService.list(Restrictions.eq("pid", planNodeId), Restrictions.or(Restrictions.eq("type", NODE_TYPE_PLAN), Restrictions.eq("type", NODE_TYPE_TASK)));
            taskNodeList.forEach(taskNode -> {
                String status = taskNode.getStatus();
                if (status.equals(ManagerStatusEnum.PROCESSING.toString()) || status.equals(ManagerStatusEnum.UNSTART.toString())) {
                    throw new OrientBaseAjaxException("", "计划下存在没有完成的任务或子计划，无法提交");
                }
            });
            //修改状态，升级版本
            modifyStatusAndIncreaseVersion(planNodeId);
        } else {
            throw new OrientBaseAjaxException("", "计划未启动或已经结束，无法提交！");
        }

    }

    private void modifyStatusAndIncreaseVersion(String planNodeId) {
        //获取现在的节点版本号
        CollabNode oldPlanNode = collabNodeService.getById(planNodeId);
        Integer oldNodeVersion = oldPlanNode.getVersion();
        //修改CB_PLAN_240中项目的状态为完成，设置项目实际完成时间，注意修改时会走版本升级的切面，不需要手动提升版本
        IBusinessModel planBM = businessModelService.getBusinessModelBySName(PLAN, COLLAB_SCHEMA_ID, Table);
        planBM.setReserve_filter("AND ID = '" + oldPlanNode.getBmDataId() + "'");
        List<Map> planMapList = orientSqlEngine.getBmService().createModelQuery(planBM).list();
        if (!CommonTools.isEmptyList(planMapList)) {
            Map planMap = planMapList.get(0);
            String id = CommonTools.Obj2String(planMap.get("id"));
            planMap.put("STATUS_" + planBM.getId(), ManagerStatusEnum.DONE.toString());
            planMap.put("ACTUAL_END_DATE_" + planBM.getId(), CommonTools.FormatDate(new Date(), "yyyy-MM-dd"));
            orientSqlEngine.getBmService().updateModelData(planBM, planMap, id);
        }
        //获取升级后的节点版本号
        CollabNode newPlanNode = collabNodeService.getById(planNodeId);
        Integer newNodeVersion = newPlanNode.getVersion();
        /*//复制并升级计划节点的研发数据版本
        devDataCopyBusiness.copyDevData(planNodeId, oldNodeVersion, newNodeVersion, NODE_TYPE_PLAN);*/
        //启动下一个计划
        CollabDevNodeDTO projectNode = structureBusiness.getRootNode(newPlanNode.getId(), newPlanNode.getVersion());
        planStartBusiness.startPlans(projectNode.getId());
    }

    @Autowired
    ICollabNodeService collabNodeService;

    @Autowired
    IBusinessModelService businessModelService;

    @Autowired
    ISqlEngine orientSqlEngine;

    @Autowired
    StructureBusiness structureBusiness;

    @Autowired
    PlanStartBusiness planStartBusiness;

    @Autowired
    DefaultCollabVersionMng defaultCollabVersionMng;

    @Autowired
    DevDataCopyBusiness devDataCopyBusiness;

}
