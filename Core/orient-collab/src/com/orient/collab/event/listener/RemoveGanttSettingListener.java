package com.orient.collab.event.listener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.event.ProjectTreeNodeDeletedEvent;
import com.orient.collab.event.ProjectTreeNodeDeletedEventParam;
import com.orient.collab.model.GanttPlanDependency;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengbin on 16/8/25.
 * Purpose:
 * Detail: if delete targets contains project object then delete gantt setting info
 * delete plan relations
 * delete base line info
 * delete base line audit flow info
 */
@Service
public class RemoveGanttSettingListener extends OrientEventListener {
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
            List<GanttPlanDependency> dependencies = new ArrayList<>();
            if (CollabConstants.PROJECT.equals(treeDeleteTarget.getModelName())) {
                dependencies = this.orientSqlEngine.getTypeMappingBmService().get(GanttPlanDependency.class,
                        new CustomerFilter(GanttPlanDependency.BELONGED_PROJECT_ID, EnumInter.SqlOperation.Equal, treeDeleteTarget.getDataId()));
                //delete base line audit flow info

            } else if (CollabConstants.PLAN.equals(treeDeleteTarget.getModelName())) {
                CustomerFilter startCustomerFilter = new CustomerFilter(GanttPlanDependency.START_PLAN_ID, EnumInter.SqlOperation.Equal, treeDeleteTarget.getDataId(), EnumInter.SqlConnection.Or);
                CustomerFilter endCustomerFilter = new CustomerFilter(GanttPlanDependency.FINISH_PLAN_ID, EnumInter.SqlOperation.Equal, treeDeleteTarget.getDataId(), EnumInter.SqlConnection.And);
                endCustomerFilter.setParent(startCustomerFilter);
                dependencies = this.orientSqlEngine.getTypeMappingBmService().get(GanttPlanDependency.class, endCustomerFilter);
            }
            dependencies.forEach(ganttPlanDependency -> orientSqlEngine.getTypeMappingBmService().delete(GanttPlanDependency.class, ganttPlanDependency.getId(), false));
        });
    }

    @Autowired
    protected ISqlEngine orientSqlEngine;

}
