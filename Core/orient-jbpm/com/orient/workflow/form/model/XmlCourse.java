package com.orient.workflow.form.model;

import org.dom4j.Element;

import com.orient.utils.CommonTools;




/**
 *@Function Name:  XmlCourse
 *@Description: 过程管理表单
 *@Date Created:  2012-5-30 上午09:03:42
 *@Author: Pan Duan Duan
 *@Last Modified:    ,  Date Modified:   
 */
public class XmlCourse
{
	
	//processName is 子流程名称
	private String processName;
	//taskName is 任务名称
	private String taskName;
	
	/**
	 *@ XmlCourse.java is 默认构造函数 
	 */
	public XmlCourse()
	{
		
	}
	/**
	 *@ XmlCourse.java is 构造函数 
	 */
	public XmlCourse(Element courseElement){
		processName = CommonTools.Obj2String(courseElement.attributeValue("process"));
		taskName = CommonTools.Obj2String(courseElement.attributeValue("task"));		
	}
	public String getProcessName()
	{
		return processName;
	}
	public void setProcessName(String processName)
	{
		this.processName = processName;
	}
	public String getTaskName()
	{
		return taskName;
	}
	public void setTaskName(String taskName)
	{
		this.taskName = taskName;
	}
}
