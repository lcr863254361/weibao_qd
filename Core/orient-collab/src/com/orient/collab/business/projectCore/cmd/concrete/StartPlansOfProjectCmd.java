package com.orient.collab.business.projectCore.cmd.concrete;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.business.PlanTaskProcessBusiness;
import com.orient.collab.business.projectCore.algorithm.GanttGraph;
import com.orient.collab.business.projectCore.cmd.Command;
import com.orient.collab.business.projectCore.cmd.CommandService;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.GanttPlanDependency;
import com.orient.collab.model.Plan;
import com.orient.collab.model.Project;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sqlengine.api.ITypeMappingBmService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * start plans of a project
 *
 * @author Seraph
 *         2016-07-22 上午10:22
 */
public class StartPlansOfProjectCmd implements Command<List<Plan>> {

    public StartPlansOfProjectCmd(Project project) {
        this.project = project;
    }

    @Override
    public List<Plan> execute() throws Exception {
        PlanTaskProcessBusiness planTaskProcessBusiness = OrientContextLoaderListener.Appwac.getBean(PlanTaskProcessBusiness.class);
        ISqlEngine sqlEngine = OrientContextLoaderListener.Appwac.getBean(ISqlEngine.class);
        ITypeMappingBmService bmService = sqlEngine.getTypeMappingBmService();
        CommandService commandService = OrientContextLoaderListener.Appwac.getBean(CommandService.class);

        List<Plan> plans = commandService.execute(new GetAllSubPlansCmd(project, sqlEngine));
        List<GanttPlanDependency> dependencies = bmService
                .get(GanttPlanDependency.class, new CustomerFilter("blngProjectId", EnumInter.SqlOperation.Equal, project.getId()));

        GanttGraph graph = new GanttGraph(plans, dependencies);
        if (graph.hasCycle()) {
            throw new IllegalArgumentException("甘特图存在环形依赖,请检查!");
        }

        graph.travelGraphAndGetToStartPlans();
        List<Plan> toStartPlans = graph.getToStartPlans();
        String sDate = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        for (Plan plan : toStartPlans) {
            plan.setActualStartDate(sDate);
            bmService.update(plan);
            //启动关联的流程
            if (CollabConstants.COLLAB_AUTO_EXECUTE_PLAN) {
                planTaskProcessBusiness.start(CollabConstants.PLAN, plan.getId());
            }
        }
        return toStartPlans;
    }

    private Project project;
}
