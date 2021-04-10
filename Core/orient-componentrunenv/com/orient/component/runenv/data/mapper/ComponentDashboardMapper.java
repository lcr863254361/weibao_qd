package com.orient.component.runenv.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.orient.component.runenv.model.ComponentDashboard;

public interface ComponentDashboardMapper {
	
	public  void saveData(ComponentDashboard dashboardParam);
	
	public ComponentDashboard getComponentDashboardByTaskId(@Param(value="taskId") String taskId);
	
	public List<ComponentDashboard> getComponentDashboardByProjctId(@Param(value="projId") String projId);

}
