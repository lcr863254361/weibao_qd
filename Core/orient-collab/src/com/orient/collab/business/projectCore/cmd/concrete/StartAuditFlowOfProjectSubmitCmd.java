package com.orient.collab.business.projectCore.cmd.concrete;

import com.orient.collab.business.projectCore.cmd.Command;
import com.orient.collab.model.Plan;
import com.orient.collab.model.Project;

import java.util.List;

/**
 * start an audit flow to process project submit
 *
 * @author Seraph
 *         2016-07-25 上午8:52
 */
public class StartAuditFlowOfProjectSubmitCmd implements Command<Boolean> {

    public StartAuditFlowOfProjectSubmitCmd(Project project){
        this.project = project;
    }

    @Override
    public Boolean execute() throws Exception {
        return true;
    }

    private Project project;
}
