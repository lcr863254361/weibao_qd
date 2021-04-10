package com.orient.collab.event.listener;

import com.orient.collab.business.DataFlowBusiness;
import com.orient.collab.business.DataTaskBusiness;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.event.ProjectTreeNodeDeletedEvent;
import com.orient.collab.event.ProjectTreeNodeDeletedEventParam;
import com.orient.sysmodel.service.collab.ICollabRoleService;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

/**
 * Created by mengbin on 16/8/25.
 * Purpose:
 * Detail: 项目树节点删除后,要删除对应的数据流的描述信息和当前的数据任务
 */
@Service
public class RemoveDataFlowListener extends OrientEventListener {
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

        param.getTreeDeleteResult().getTreeDeleteTargets().forEach(treeDeleteTarget -> {
            if (CollabConstants.TASK.equals(treeDeleteTarget.getModelName())) {
                dataFlowBusiness.deleteDataFlowActivity(treeDeleteTarget.getDataId());
                dataTaskBusiness.deleteCurCollabTaskByTaskId(treeDeleteTarget.getDataId());
                //delete history data task
                dataTaskBusiness.deleteHisDataTaskByTaskId(treeDeleteTarget.getDataId());
            }
        });
    }

    @Autowired
    private ICollabRoleService collabRoleService;

    @Autowired
    private DataFlowBusiness dataFlowBusiness;

    @Autowired
    private DataTaskBusiness dataTaskBusiness;


}
