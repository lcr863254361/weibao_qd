package com.orient.collab.controller;

import com.orient.collab.business.ProjectProcessBusiness;
import com.orient.collab.business.projectCore.exception.CollabFlowControlException;
import com.orient.collab.business.projectCore.exception.StateIllegalException;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * the controller process the project's progress
 * i.e the WBS driven flow
 *
 * @author Seraph
 *         2016-07-21 上午9:56
 */
@Controller
@RequestMapping("/project")
public class ProjectProcessController extends BaseController{

    /**
     * to start a project, the baseline mush have been audited and approved
     * @param projectId
     * @return
     */
    @RequestMapping("/start")
    @ResponseBody
    public CommonResponseData startProject(String projectId){
        return this.projectProcessBusiness.startProject(projectId);
    }

    @RequestMapping("/commit")
    @ResponseBody
    public CommonResponseData commitProject(String projectId){
        return this.projectProcessBusiness.commitProject(projectId);
    }

    @Autowired
    private ProjectProcessBusiness projectProcessBusiness;
}
