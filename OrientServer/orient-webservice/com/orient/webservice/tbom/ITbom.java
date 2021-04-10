package com.orient.webservice.tbom;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface ITbom {

	public abstract String IsLock(String tbomname, String schemaid);

	@Transactional(propagation = Propagation.REQUIRED)
	public abstract String saveTbomOrder(String undateTbomOrderStr);

	@Transactional(propagation = Propagation.REQUIRED)
	public abstract String setTbom(String str, String schemaid,
			String username, String tbomName);

	public abstract String getTbom(String schemaIdTbomID);

	public abstract String getTbomName();

	public abstract String setLock(String id, String schemaid, Long lock,
			String username);

	public abstract String deleteTbom(String tbomname, String schemaid,
			String username);

	@Transactional(propagation = Propagation.REQUIRED)
	public abstract String AutoCreateTbom(String str);

	public String getSource();
	
	public String getColumn(String tableid);
	
	public String getRole();


	/**
	 * @param modelId 模型ID 可以是Model 也可以是 View
	 * @return 所绑定模型的模板信息[{ID:'1',NAME:'XXXX'}]
	 */
	List<Map<String, String>> getModelTemplatesByModelId(String modelId);
	
}