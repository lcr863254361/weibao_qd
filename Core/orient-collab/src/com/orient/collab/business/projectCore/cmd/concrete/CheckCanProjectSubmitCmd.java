package com.orient.collab.business.projectCore.cmd.concrete;

import com.orient.collab.business.projectCore.cmd.Command;
import com.orient.collab.business.projectCore.cmd.CommandService;
import com.orient.collab.model.Plan;
import com.orient.collab.model.Project;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.web.base.CommonResponseData;

import java.util.List;

import static com.orient.collab.config.CollabConstants.STATUS_FINISHED;

/**
 * check whether a project can submit
 *
 * @author Seraph
 *         2016-07-25 上午9:23
 */
public class CheckCanProjectSubmitCmd implements Command<CommonResponseData>{

    public CheckCanProjectSubmitCmd(Project project){
        this.project = project;
    }

    @Override
    public CommonResponseData execute() throws Exception {
        CommonResponseData retV = new CommonResponseData(true, "");

        ISqlEngine sqlEngine = OrientContextLoaderListener.Appwac.getBean(ISqlEngine.class);
        CommandService commandService = OrientContextLoaderListener.Appwac.getBean(CommandService.class);

        List<Plan> plans = commandService.execute(new GetAllSubPlansCmd(project, sqlEngine));
        for(Plan plan : plans){
            if(!STATUS_FINISHED.equals(plan.getStatus())){
                retV.setSuccess(false);
                retV.setMsg("有计划未完成,不能提交");
                break;
            }
        }

        return retV;
    }

    private Project project;
}
