package com.orient.workflow.form;

import java.util.Set;

import com.orient.workflow.form.model.XmlFormReader;

/**
 * @ClassName EdmFormCache
 * 流程表单的缓存类
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

public interface EdmFormCache {
	/** 
	 * 设置改流程定义ID所对应的表单Reader
	 * @Method: set 
	 * @param pdId
	 * @param reader 
	 */
	void set(String pdId, XmlFormReader reader);

	/** 
	 * 获取该流程定义ID的表单Reader
	 * @Method: get 
	 * @param pdId
	 * @return 
	 */
	XmlFormReader get(String pdId);

	/** 
	 * 获取所有缓存表单所对应的流程定义ID
	 * @Method: getCachedProcessDefIds 
	 * @return 
	 */
	Set<String> getCachedProcessDefIds();

	/** 
	 * 清楚某一流程定义所对应的表单Reader
	 * @Method: remove 
	 * @param pdId 
	 */
	void remove(String pdId);

	/** 
	 * 清楚缓存
	 * @Method: clear  
	 */
	void clear();
}
