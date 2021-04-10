package com.orient.collab.business.projectCore.cmd.concrete;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.business.projectCore.cmd.Command;
import com.orient.collab.model.Plan;
import com.orient.collab.model.Project;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sqlengine.api.ITypeMappingBmService;
import com.orient.utils.UtilFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * get all subplans of a project
 *
 * @author Seraph
 *         2016-07-25 上午8:56
 */
public class GetAllSubPlansCmd implements Command<List<Plan>> {

    public GetAllSubPlansCmd(Project project, ISqlEngine sqlEngine){
        this.project = project;
        this.sqlEngine = sqlEngine;
    }

    public GetAllSubPlansCmd(Plan rootPlan, ISqlEngine sqlEngine){
        this.rootPlan = rootPlan;
        this.sqlEngine = sqlEngine;
    }

    @Override
    public List<Plan> execute() throws Exception {
        ITypeMappingBmService bmService = sqlEngine.getTypeMappingBmService();
        List<Plan> plans = UtilFactory.newArrayList();

        if(project != null){
            plans.addAll(bmService.get(Plan.class, new CustomerFilter("parProjectId", EnumInter.SqlOperation.Equal, project.getId())));
            List<Plan> rootPlans = new ArrayList<>(plans);
            for(Plan plan : rootPlans){
                getSubPlans(plan, plans, bmService);
            }
        }else if(rootPlan != null){
            getSubPlans(rootPlan, plans, bmService);
        }

        return plans;
    }

    private void getSubPlans(Plan parPlan, List<Plan> allPlans, ITypeMappingBmService bmService){
        List<Plan> childPlans = bmService.get(Plan.class, new CustomerFilter("parPlanId", EnumInter.SqlOperation.Equal, parPlan.getId()));
        if(childPlans.size() == 0){
            return;
        }

        allPlans.addAll(childPlans);
        for(Plan childPlan : childPlans){
            getSubPlans(childPlan, allPlans, bmService);
        }
    }

    private ISqlEngine sqlEngine;
    private Project project;
    private Plan rootPlan;
}
