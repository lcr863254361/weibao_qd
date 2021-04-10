package com.orient.component.runenv.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.orient.component.runenv.model.ComponentFlowData;

public interface ComponentFlowDataMapper {
	public void saveData(ComponentFlowData flowData);
	
	public void update(ComponentFlowData flowData);
	
	public void deleteAll();
	
	public void deleteProjData(@Param(value="projCode") String projCode);
	
	public void deleteKeyData(@Param(value="projCode") String projCode,@Param(value="key") String key);
	
	public List<ComponentFlowData> findAll();
	
	public ComponentFlowData getComponentFlowDataByKey(@Param(value="projCode") String projCode,@Param(value="key") String key);
	
	public List<ComponentFlowData> findByProjCode(@Param(value="projCode") String projCode);
	
}
