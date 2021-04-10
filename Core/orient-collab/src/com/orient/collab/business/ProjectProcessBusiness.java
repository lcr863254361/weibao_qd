package com.orient.collab.business;

import com.orient.collab.business.projectCore.ProjectEngine;
import com.orient.collab.business.projectCore.exception.CollabFlowControlException;
import com.orient.collab.business.projectCore.exception.StateIllegalException;
import com.orient.collab.model.Project;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * the project's process business
 *
 * @author Seraph
 *         2016-07-21 上午9:57
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class ProjectProcessBusiness extends BaseBusiness{

    public CommonResponseData startProject(String projectId) {
        Project project = this.orientSqlEngine.getTypeMappingBmService().getById(Project.class, projectId);
        return this.projectEngine.startProject(project);
    }

    public CommonResponseData commitProject(String projectId) {
        Project project = this.orientSqlEngine.getTypeMappingBmService().getById(Project.class, projectId);
        return this.projectEngine.submitProject(project);
    }

    @Autowired
    private ProjectEngine projectEngine;
}
