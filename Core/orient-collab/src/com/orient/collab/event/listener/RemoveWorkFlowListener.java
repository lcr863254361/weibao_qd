package com.orient.collab.event.listener;

import com.orient.background.bean.PDBean;
import com.orient.background.business.PDMgrBusiness;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.event.ProjectTreeNodeDeletedEvent;
import com.orient.collab.event.ProjectTreeNodeDeletedEventParam;
import com.orient.collab.model.TreeDeleteResult;
import com.orient.flow.business.ProcessDefinitionBusiness;
import com.orient.utils.CommonTools;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.RepositoryService;
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
 * Detail: delete related workflow info
 */
@Service
public class RemoveWorkFlowListener extends OrientEventListener {
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

        targetDic.forEach(targetName -> deleteWorkFlow(treeDeleteResult, targetName));
    }

    private void deleteWorkFlow(TreeDeleteResult treeDeleteResult, String targetModel) {
        List<String> targetIds = treeDeleteResult.getTreeDeleteTargets().stream().filter(treeDeleteTarget -> targetModel.equals(treeDeleteTarget.getModelName()))
                .map(treeDeleteTarget -> treeDeleteTarget.getDataId()).collect(Collectors.toList());
        if (!CommonTools.isEmptyList(targetIds)) {
            //删除流程流程定义
            RepositoryService repositoryService = processEngine.getRepositoryService();
            targetIds.forEach(targetId -> {
                String pdKey = targetModel + COLLAB_PD_NAME_SPERATOR + targetId;
                List<ProcessDefinition> processDefinitionList = processDefinitionBusiness.getAllPrcDefsWithPdKeyOrNameDescByVersion(pdKey, true);
                if (!CommonTools.isEmptyList(processDefinitionList)) {
                    processDefinitionList.forEach(processDefinition -> {
                        PDBean pdBean = new PDBean();
                        pdBean.setName(processDefinition.getName());
                        pdBean.setVersion(String.valueOf(processDefinition.getVersion()));
                        pdBean.setId(processDefinition.getId());
                        pdBean.setType("collab");
                        pdMgrBusiness.delete(pdBean);
                    });
                }
            });
        }
    }

    @Autowired
    ProcessDefinitionBusiness processDefinitionBusiness;

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    PDMgrBusiness pdMgrBusiness;

}
