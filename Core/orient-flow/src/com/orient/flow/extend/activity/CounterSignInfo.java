
package com.orient.flow.extend.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
/**
 * @ClassName CounterSignInfo
 * 会签信息(策略)描述对象
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-5
 */

public class CounterSignInfo implements Serializable{

	private static final long				serialVersionUID	= 9146195929986124354L;
	// 会签人列表[会签人做晚任务则会自动添加到该列表]
	private List<Map<String, String>>	counterSignList	= new ArrayList<Map<String, String>>();
	// 会签总人数
	private int									userCount			= 0;
	// 会签设定的策略
	private String								strategy;
	// 根据会签设定的策略和会签总人数计算出的策略结果
	private String								strategyValue;
	// 
	private boolean							autoCompleteTask;

	public List<Map<String, String>> getCounterSignList(){
		return counterSignList;
	}

	public void setCounterSignList(List<Map<String, String>> counterSignList){
		this.counterSignList = counterSignList;
	}

	public String getStrategyValue(){
		return strategyValue;
	}

	public void setStrategyValue(String strategyValue){
		this.strategyValue = strategyValue;
	}

	public int getUserCount(){
		return userCount;
	}

	public void setUserCount(int userCount){
		this.userCount = userCount;
	}

	public String getStrategy(){
		return strategy;
	}

	public void setStrategy(String strategy){
		this.strategy = strategy;
	}

	public boolean isAutoCompleteTask(){
		return autoCompleteTask;
	}

	public void setAutoCompleteTask(boolean autoCompleteTask){
		this.autoCompleteTask = autoCompleteTask;
	}
}
