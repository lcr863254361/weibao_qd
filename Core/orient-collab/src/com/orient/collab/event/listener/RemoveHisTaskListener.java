package com.orient.collab.event.listener;

import com.orient.collab.config.CollabConstants;
import com.orient.collab.event.ProjectTreeNodeDeletedEvent;
import com.orient.collab.event.ProjectTreeNodeDeletedEventParam;
import com.orient.collab.model.TreeDeleteResult;
import com.orient.flow.business.ProcessDefinitionBusiness;
import com.orient.sysmodel.dao.flow.FlowTaskDAO;
import com.orient.sysmodel.domain.his.CwmSysHisTaskEntity;
import com.orient.sysmodel.service.flow.IHisTaskService;
import com.orient.utils.CommonTools;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.hibernate.criterion.Restrictions;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.history.HistoryActivityInstance;
import org.jbpm.pvm.internal.history.model.HistoryActivityInstanceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.orient.collab.config.CollabConstants.COLLAB_PD_NAME_SPERATOR;

/**
 * Created by mengbin on 16/8/25.
 * Purpose:
 * Detail: delete custom history tasks
 * delete history plan
 * delete history collab task
 * delete history data task
 */
@Service
public class RemoveHisTaskListener extends OrientEventListener {
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return ProjectTreeNodeDeletedEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }

        OrientEvent orientEvent = (OrientEvent) event;
        ProjectTreeNodeDeletedEventParam param = (ProjectTreeNodeDeletedEventParam) orientEvent.getParams();

        if (!param.getTreeDeleteResult().getDeleteSuccess()) {
            orientEvent.aboardEvetn();
            return;
        }

        List<String> targetDic = new ArrayList<String>() {{
            add(CollabConstants.PLAN);
            add(CollabConstants.TASK);
        }};

        TreeDeleteResult treeDeleteResult = param.getTreeDeleteResult();

        targetDic.forEach(targetName -> deleteHistoryTask(treeDeleteResult, targetName));
    }

    private void deleteHistoryTask(TreeDeleteResult treeDeleteResult, String targetModel) {
        List<String> targetIds = treeDeleteResult.getTreeDeleteTargets().stream().filter(treeDeleteTarget -> targetModel.equals(treeDeleteTarget.getModelName()))
                .map(treeDeleteTarget -> treeDeleteTarget.getDataId()).collect(Collectors.toList());
        if (!CommonTools.isEmptyList(targetIds)) {
            targetIds.forEach(targetId -> {
                String pdKey = targetModel + COLLAB_PD_NAME_SPERATOR + targetId;
                List<ProcessDefinition> processDefinitionList = processDefinitionBusiness.getAllPrcDefsWithPdKeyOrNameDescByVersion(pdKey, true);
                //delete history collab tasks
                List<String> collabFlowTaskIds = new ArrayList<>();
                if (!CommonTools.isEmptyList(processDefinitionList)) {
                    HistoryService historyService = processEngine.getHistoryService();
                    processDefinitionList.forEach(processDefinition -> {
                        String pdId = processDefinition.getId();
                        List<HistoryActivityInstance> historyActivityInstances = historyService.createHistoryActivityInstanceQuery().processDefinitionId(pdId).list();
                        historyActivityInstances.forEach(historyActivityInstance -> {
                            HistoryActivityInstanceImpl historyActivityInstance1 = (HistoryActivityInstanceImpl) historyActivityInstance;
                            String actHisDBId = String.valueOf(historyActivityInstance1.getDbid());
                            String flowTaskId = flowTaskDAO.getFlowTaskIdByHisActivityDBId(actHisDBId);
                            collabFlowTaskIds.add(flowTaskId);
                        });
                    });
                }
                List<CwmSysHisTaskEntity> toDels = new ArrayList<CwmSysHisTaskEntity>();
                if (!CommonTools.isEmptyList(collabFlowTaskIds)) {
                    List<CwmSysHisTaskEntity> cwmSysHisTaskEntities = hisTaskService.list(Restrictions.eq("taskType", "COLLAB"), Restrictions.in("taskId", collabFlowTaskIds));
                    toDels.addAll(cwmSysHisTaskEntities);
                }
                //delete history data tasks
                if (CollabConstants.TASK.equals(targetModel)) {
                    List<CwmSysHisTaskEntity> cwmSysHisTaskEntities = hisTaskService.list(Restrictions.eq("taskType", "DATA"), Restrictions.in("taskId", targetIds));
                    toDels.addAll(cwmSysHisTaskEntities);
                }
                if (CollabConstants.PLAN.equals(targetModel)) {
                    //delete history plan tasks
                    List<CwmSysHisTaskEntity> cwmSysHisTaskEntities = hisTaskService.list(Restrictions.eq("taskType", "PLAN"), Restrictions.in("taskId", targetIds));
                    toDels.addAll(cwmSysHisTaskEntities);
                }
                toDels.forEach(cwmSysHisTaskEntity -> hisTaskService.delete(cwmSysHisTaskEntity));
            });
        }

    }

    @Autowired
    ProcessDefinitionBusiness processDefinitionBusiness;

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    private FlowTaskDAO flowTaskDAO;

    @Autowired
    IHisTaskService hisTaskService;

}
