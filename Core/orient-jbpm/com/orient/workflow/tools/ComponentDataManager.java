package com.orient.workflow.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于在组件提交时，将组件的输出传递给任务处理事件。
 * 保存时key为当前流程任务的id(flowTaskId)，value为该组件的输出json串
 *@Function Name:  ComponentDataManager
 *@Description:
 *@Date Created:  2015-7-6 下午02:28:42
 *@Author: changxk
 *@Last Modified:    ,  Date Modified:
 */
public class ComponentDataManager {

	
	// key is flowTaskId
	private static Map<String,String> componentData = new HashMap<String, String>();
	
	//key is flowTaskId
	private static Map<String,String> componentDashboardParam = new HashMap<String, String>();
	
	
	public static String getComponentData(String key)
	{
		String strComponentData = componentData.get(key);
		if(strComponentData != null && !"".equals(strComponentData)){
			componentData.remove(key);
		}
		return strComponentData;
		
	}
		
	public static void setComponentData(String key, String value)
	{
		componentData.put(key, value);
	}
	
	public static String getComponentDashboardParam(String flowTaskId)
	{

		String param = componentDashboardParam.get(flowTaskId);
		if(param != null && !"".equals(param)){
			componentDashboardParam.remove(param);
		}
		return param;
	}
	
	public static void setComponentDashboardParam(String flowTaskId, String param)
	{
		componentDashboardParam.put(flowTaskId, param);
	}
}

