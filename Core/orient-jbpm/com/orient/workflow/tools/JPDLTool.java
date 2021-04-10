package com.orient.workflow.tools;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import com.orient.jpdl.model.ActivityGroup;
import com.orient.jpdl.model.End;
import com.orient.jpdl.model.Fork;
import com.orient.jpdl.model.Join;
import com.orient.jpdl.model.Process;
import com.orient.jpdl.model.ProcessChoice;
import com.orient.jpdl.model.ProcessChoiceItem;
import com.orient.jpdl.model.Start;
import com.orient.jpdl.model.SubProcess;
import com.orient.jpdl.model.SubProcessChoice;
import com.orient.jpdl.model.SubProcessChoiceItem;
import com.orient.jpdl.model.Task;
import com.orient.jpdl.model.TaskChoice;
import com.orient.jpdl.model.TaskChoiceItem;
import com.orient.utils.CommonTools;
import com.orient.workflow.form.model.TaskPlan;


/**
 * a jpdl tool used to get jpdl content
 */
public class JPDLTool {
	
	static public JPDLTool getJPDLTool(){
		return new JPDLTool();
	}

	public String createJpdlContentFromTaskPlanList(List<TaskPlan> taskPlans, String processName, String type) {
		
		//创建流程对象
		Process process = new Process();
		process.setName(processName);
		process.setCategory(type);

		
		ProcessChoice pc = new ProcessChoice();	
		process.setProcessChoice(pc);
		ProcessChoiceItem[] pcItemArr = new ProcessChoiceItem[taskPlans.size()+2];
		ProcessChoiceItem pcItem = new ProcessChoiceItem();
		ActivityGroup group = new ActivityGroup();
		//开始节点
		int x = 100;
		int y = 100;
		Start start = new Start(); 
		start.setName("开始1");
		start.setG(x+25 + "," + y +",92,52");
		group.setStart(start);	
		pcItem.setActivityGroup(group);
		pcItemArr[0]=pcItem;
		for(int i=0;i<taskPlans.size();i++){
			//循环遍历
			TaskPlan node = taskPlans.get(i);
			y = y + 100;
			//如果是子流程
			if(node.isSubTask()){
				SubProcess subPro = new SubProcess();
				subPro.setG(x + "," +y + ",92,52");
				subPro.setName(node.getTaskName()); 
				subPro.setSubProcessKey(node.getTaskName());
				pcItem = new ProcessChoiceItem();
				group = new ActivityGroup();
				group.setSubProcess(subPro);	
				pcItem.setActivityGroup(group);
				pcItemArr[1+i]=pcItem;
			}else{
				//普通任务
				Task task = new Task();
				task.setG(x + "," +y + ",92,52");
				task.setName(node.getTaskName()); 
				//得到任务信息
				Map<String,Object> taskInfo = node.getTaskInfo();
				String assigner = taskInfo == null ? "" : CommonTools.Obj2String(taskInfo.get("LOGIN_NAME"));
				task.setAssignee(assigner);
				pcItem = new ProcessChoiceItem();
				group = new ActivityGroup();
				group.setTask(task);	
				pcItem.setActivityGroup(group);
				pcItemArr[1+i]=pcItem;
			}
		}
		y = y + 100;
		//结束节点
		End end = new End();
		end.setName("结束1"); 
		end.setG(x+25 + "," + y +",92,52");
		pcItem = new ProcessChoiceItem();
		group = new ActivityGroup();
		group.setEnd(end);	
		//组装
		pcItem.setActivityGroup(group);
		pcItemArr[1+taskPlans.size()]=pcItem;
		pc.setProcessChoiceItem(pcItemArr);
		StringWriter sw = new StringWriter();
		try {
			process.marshal(sw);
			sw.close();
		} catch (MarshalException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sw.toString();
	}
	
	public String syncExistJpdlContentWithTaskPlanList(Process process, List<TaskPlan> taskPlans){
		
		Process realProcess = new Process();
		realProcess.setName(process.getName());
		realProcess.setCategory(process.getCategory());
		
		//遍历数据库取出来的jpdl流程
		ProcessChoice oriProcessChoice = process.getProcessChoice();
		ProcessChoiceItem[] oriItems = oriProcessChoice.getProcessChoiceItem();
		//过滤出分支 以及 合并
		List<ProcessChoiceItem> otherItems = new ArrayList<ProcessChoiceItem>();
		for(ProcessChoiceItem item : oriItems){
			ActivityGroup activityGroup = item.getActivityGroup();
			Object choiceValue = activityGroup.getChoiceValue();
			if(choiceValue instanceof Fork || choiceValue instanceof Join)
			{
				otherItems.add(item);
			}
		}
		ProcessChoice pc = new ProcessChoice();	
		ProcessChoiceItem[] pcItemArr = new ProcessChoiceItem[taskPlans.size()+2+otherItems.size()];
		//装配 开始 以及 结束节点
		for(ProcessChoiceItem item : oriItems){
			ActivityGroup activityGroup = item.getActivityGroup();
			Object choiceValue = activityGroup.getChoiceValue();
			if(choiceValue instanceof Start)
			{
				pcItemArr[0] = item;
			}else if(choiceValue instanceof End)
			{
				pcItemArr[1+taskPlans.size()] = item;
			}
		}
		//装配特殊节点
		for(int i=0;i<otherItems.size();i++){
			ProcessChoiceItem item = otherItems.get(i);
			pcItemArr[2+taskPlans.size()+i] = item;
		}
		syncNode(taskPlans,oriItems,pcItemArr);
		pc.setProcessChoiceItem(pcItemArr);
		realProcess.setProcessChoice(pc);
		
		StringWriter sw = new StringWriter();
		//返回结果
		String retStr = "";
		try {
			realProcess.marshal(sw);
			sw.close();
			retStr = sw.toString();
		} catch (MarshalException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retStr;
	}
	
	private void syncNode(List<TaskPlan> taskPlans, ProcessChoiceItem[] oriItems, ProcessChoiceItem[] pcItemArr) {
		
		//装配 任务
		int i = 0;
		for(TaskPlan taskPlan : taskPlans)
		{
			//任务名称
			String taskName = taskPlan.getTaskName();
			boolean choosen = false;
			//遍历已经存在的节点信息
			for(ProcessChoiceItem item : oriItems)
			{
				ActivityGroup activityGroup = item.getActivityGroup();
				Object choiceValue = activityGroup.getChoiceValue();
				if(choiceValue instanceof SubProcess)
				{
					SubProcess oriSubProcess = (SubProcess)choiceValue;
					if(oriSubProcess.getName().equals(taskName))
					{
						choosen = true;
						//同步
						ProcessChoiceItem tempPcItem = new ProcessChoiceItem();
						ActivityGroup tempGroup = new ActivityGroup();
						//判断类型是否一致
						if(taskPlan.isSubTask()){
							//如果是子流程直接装配
							tempGroup.setSubProcess(oriSubProcess);	
						}else{
							//如果转化为任务
							Task task = converSubProcessToTask(oriSubProcess,taskPlan);
							tempGroup.setTask(task);
						}
						tempPcItem.setActivityGroup(tempGroup);
						pcItemArr[1+i]=tempPcItem;
						break;
					}
				}else if(choiceValue instanceof Task)
				{
					Task oriTask = (Task)choiceValue;
					if(oriTask.getName().equals(taskName))
					{
						choosen = true;
						ProcessChoiceItem tempPcItem = new ProcessChoiceItem();
						ActivityGroup tempGroup = new ActivityGroup();
						if(!taskPlan.isSubTask()){
							//如果类型一致
							//同步 主要同步责任人
							Map<String,Object> taskInfo = taskPlan.getTaskInfo();
							String assigner = taskInfo == null ? "" : CommonTools.Obj2String(taskInfo.get("LOGIN_NAME"));
							oriTask.setAssignee(assigner);
							tempGroup.setTask(oriTask);
						}else{
							SubProcess subProcess = converTaskToSubProcess(oriTask, taskPlan);
							tempGroup.setSubProcess(subProcess);
						}
						tempPcItem.setActivityGroup(tempGroup);
						pcItemArr[1+i] = tempPcItem;
						break;
					}
				}
			}
			if(!choosen)
			{
				Random rand = new Random();
				int x = rand.nextInt(500);
				int y = rand.nextInt(500);
				//如果没有找到 则新增
				//如果是子流程
				if(taskPlan.isSubTask()){
					SubProcess subPro = new SubProcess();
					subPro.setG(x + "," +y + ",92,52");
					subPro.setName(taskPlan.getTaskName()); 
					subPro.setSubProcessKey(taskPlan.getTaskName());
					ProcessChoiceItem tempPcItem = new ProcessChoiceItem();
					ActivityGroup tempGroup = new ActivityGroup();
					tempGroup.setSubProcess(subPro);	
					tempPcItem.setActivityGroup(tempGroup);
					pcItemArr[1+i]=tempPcItem;
				}else{
					//普通任务
					Task task = new Task();
					task.setG(x + "," +y + ",92,52");
					task.setName(taskPlan.getTaskName()); 
					Map<String,Object> taskInfo = taskPlan.getTaskInfo();
					String assigner = taskInfo == null ? "" : CommonTools.Obj2String(taskInfo.get("LOGIN_NAME"));
					task.setAssignee(assigner);
					ProcessChoiceItem tempPcItem = new ProcessChoiceItem();
					ActivityGroup tempGroup = new ActivityGroup();
					tempGroup.setTask(task);
					tempPcItem.setActivityGroup(tempGroup);
					pcItemArr[1+i]=tempPcItem;
				}
			}
			i++;
		}
	}
	
	private Task converSubProcessToTask(SubProcess oriSubProcess, TaskPlan taskPlan) {
		Task task = new Task();
		//设置基本信息
		task.setG(oriSubProcess.getG());
		task.setName(oriSubProcess.getName()); 
		//设置流转信息
		SubProcessChoiceItem[] subProcessChoiceItems = oriSubProcess.getSubProcessChoice().getSubProcessChoiceItem();
		TaskChoiceItem[] taskChoiceItems = new TaskChoiceItem[subProcessChoiceItems.length];
		for(int index=0; index<subProcessChoiceItems.length;index++){
			SubProcessChoiceItem spChoi = subProcessChoiceItems[index];
			taskChoiceItems[index] = new TaskChoiceItem();
			taskChoiceItems[index].setOn(spChoi.getOn());
			taskChoiceItems[index].setTimer(spChoi.getTimer());
			taskChoiceItems[index].setTransition(spChoi.getTransition());
		}
		TaskChoice taskChoice = new TaskChoice();
		taskChoice.setTaskChoiceItem(taskChoiceItems);
		task.setTaskChoice(taskChoice);
		Map<String,Object> taskInfo = taskPlan.getTaskInfo();
		String assigner = taskInfo == null ? "" : CommonTools.Obj2String(taskInfo.get("LOGIN_NAME"));
		task.setAssignee(assigner);
		return task;
	}
	
	private SubProcess converTaskToSubProcess(Task oriTask, TaskPlan taskPlan) {
		
		SubProcess subPro = new SubProcess();
		subPro.setG(oriTask.getG());
		subPro.setName(taskPlan.getTaskName()); 
		subPro.setSubProcessKey(taskPlan.getTaskName());
		//设置流转信息
		TaskChoiceItem[] taskChoiceItems = oriTask.getTaskChoice().getTaskChoiceItem();
		SubProcessChoiceItem[] subProcessChoiceItems = new SubProcessChoiceItem[taskChoiceItems.length];
		for(int index=0; index<taskChoiceItems.length;index++){
			TaskChoiceItem tci = taskChoiceItems[index];
			subProcessChoiceItems[index] = new SubProcessChoiceItem();
			subProcessChoiceItems[index].setOn(tci.getOn());
			subProcessChoiceItems[index].setTimer(tci.getTimer());
			subProcessChoiceItems[index].setTransition(tci.getTransition());
		}
		SubProcessChoice subProcessChoice = new SubProcessChoice();
		subProcessChoice.setSubProcessChoiceItem(subProcessChoiceItems);
		subPro.setSubProcessChoice(subProcessChoice);
		return subPro;
	}
	
}
