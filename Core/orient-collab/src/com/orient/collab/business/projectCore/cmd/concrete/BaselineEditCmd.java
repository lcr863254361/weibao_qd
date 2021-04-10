package com.orient.collab.business.projectCore.cmd.concrete;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collab.business.GanttBusiness;
import com.orient.collab.business.projectCore.cmd.Command;
import com.orient.collab.model.GanttPlan;
import com.orient.collab.model.Project;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sqlengine.api.ISqlEngine;

import java.util.List;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.collab.config.CollabConstants.PLAN;
import static com.orient.collab.config.CollabConstants.PROJECT;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-09-13 下午5:54
 */
public class BaselineEditCmd implements Command<List<Void>> {

    public BaselineEditCmd(Project project, boolean rollback, ISqlEngine orientSqlEngine, IBusinessModelService businessModelService){
        this.project = project;
        this.rollback = rollback;
        this.orientSqlEngine = orientSqlEngine;
        this.businessModelService = businessModelService;
    }

    @Override
    public List<Void> execute() throws Exception {

        IBusinessModel planBm = this.businessModelService.getBusinessModelBySName(PLAN, COLLAB_SCHEMA_ID, Table);

        GanttBusiness ganttBusiness = OrientContextLoaderListener.Appwac.getBean(GanttBusiness.class);
        List<GanttPlan> allPlans = ganttBusiness.getSubPlansCascade(PROJECT, project.getId());
        for(GanttPlan plan : allPlans){
            doSetBaseline(plan, planBm);
        }

        return null;
    }

    private void restoreBaselineCascade(GanttPlan parPlan, IBusinessModel planBm){
        if(parPlan.getChildren()==null || parPlan.getChildren().size()<=0){
            return;
        }

        for(GanttPlan plan : parPlan.getChildren()){
            doSetBaseline(plan, planBm);
        }
    }

    private void doSetBaseline(GanttPlan plan, IBusinessModel planBm){
        if(rollback){
            plan.setActualStartDate(plan.getActualStartDate());
            plan.setActualEndDate(plan.getActualEndDate());
            plan.setPlannedStartDate(null);
            plan.setPlannedEndDate(null);
        }else{
            plan.setPlannedStartDate(plan.getActualStartDate());
            plan.setPlannedEndDate(plan.getActualEndDate());
            plan.setActualStartDate(null);
            plan.setActualEndDate(null);
        }
        this.orientSqlEngine.getBmService().updateModelData(planBm, plan, plan.getId(), true);
        restoreBaselineCascade(plan, planBm);
    }

    private Project project;
    private ISqlEngine orientSqlEngine;
    private IBusinessModelService businessModelService;
    private boolean rollback;
}
