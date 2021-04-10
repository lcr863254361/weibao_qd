/**  
* @Project: OrientEDM
* @Title: RestrictionCmd.java
* @Package com.orient.sqlengine.internal.sys.cmd
* TODO
* @author zhulc@cssrc.com.cn
* @date Apr 12, 2012 3:02:40 PM
* @Copyright: 2012 www.cssrc.com.cn. All rights reserved.
* @version V1.0  

*/


package com.orient.sqlengine.internal.sys.cmd;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.IEnum;
import com.orient.metamodel.operationinterface.IRestriction;
import com.orient.sqlengine.cmd.api.EDMCommand;
import com.orient.sqlengine.cmd.api.EDMCommandService;
import com.orient.sqlengine.internal.SqlEngineHelper;

 

/**
 * @ClassName RestrictionCmd
 * 约束命令
 * @author zhulc@cssrc.com.cn
 * @date Apr 12, 2012
 */

public class RestrictionCmd implements EDMCommand<Object> {
	private static final Logger log = Logger.getLogger(RestrictionCmd.class);
	/** 
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 1L;
	
	private StringBuilder sql = new StringBuilder();
	
	protected EDMCommandService commandService;
	
	private IRestriction res;
	
	public RestrictionCmd(IRestriction res){
		this.res = res;
	}
	
	/** 
	 * 约束为普通枚举约束时，返回普通枚举列表
	 * 否则返回null
	 * @Method: simpleEnum 
	 * @return  List<IEnum>
	 */
	public List<IEnum> simpleEnum(){
		if(res.getRestionType()!=1){
			return null;
		}
		return res.getAllEnums();
	}
	
	/** 
	 * 约束为表枚举约束时，返回表枚举列表
	 * 否则返回null
	 *
	 * @Method: tableEnum 
	 * @return  List<IEnum>
	 */
	@SuppressWarnings("unchecked")
	public List<IEnum> tableEnum(){
		if(res.getRestionType()!=2){
			return null;
		}
		sql.setLength(0);
		sql.append(res.getTableEnum().getTableEnumSql());	
		List<Map<String,Object>>  tEnums = (List<Map<String,Object>>) commandService.execute(this);
		List<IEnum> enums = new ArrayList<IEnum>();
		for (Map<String,Object> map : tEnums) {
			com.orient.metamodel.metadomain.Enum iEnum = new com.orient.metamodel.metadomain.Enum(); 
			String key = SqlEngineHelper.Obj2String(map.keySet().iterator().next());
			String v = SqlEngineHelper.Obj2String(map.get(key));
			iEnum.setValue(v);
			iEnum.setDisplayValue(v);
			iEnum.setIsopen(1L);
			enums.add(iEnum);
		}
		return enums;
	}
	
	/** 
	 * 约束为范围约束时，返回范围约束Map
	 * 否则返回null
	 *
	 * @Method: staticRange 
	 * @return  Map<String,String>
	 *           key:mindata value:***
	 *           key:maxdata value:***
	 */
	public Map<String,String> staticRange(){
		String mindata = SqlEngineHelper.Obj2String(res.getMinLength());
		String maxdata = SqlEngineHelper.Obj2String(res.getMaxLength());
		Map<String,String> rangeMap = new HashMap<String,String>();
		rangeMap.put("mindata", mindata);
		rangeMap.put("maxdata", maxdata);
		return rangeMap;
	}
	
	/** 
	 * 约束为动态范围约束时，返回范围约束Map
	 * 否则返回null
	 *
	 * @Method: dynamicRange 
	 * @return  Map<String,String>
	 *          key:mindata value:***
	 *          key:maxdata value:***
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> dynamicRange(){
		if(res.getRestionType()!=4){
			return null;
		}
		sql.setLength(0);
		IColumn maxCol = res.getTableEnum().getMaxColumn();
		IColumn minCol = res.getTableEnum().getMinColumn();
		sql.append(" select min(").append(minCol.getColumnName()).append(") as data"); 
		sql.append(" from ").append(minCol.getRefMatrix().getMainTable().getTableName());
		sql.append(" where ").append(minCol.getColumnName()).append(" is not null ");
		
		sql.append(" union all ");
		sql.append(" select max(").append(maxCol.getColumnName()).append(") as data "); 
		sql.append(" from ").append(maxCol.getRefMatrix().getMainTable().getTableName());
		sql.append(" where ").append(maxCol.getColumnName()).append(" is not null ");
		
		List<Map<String,Object>> dataList = (List<Map<String,Object>>)commandService.execute(this);
	    Map<String,String> rangeMap = new HashMap<String,String>();
	    rangeMap.put("mindata", SqlEngineHelper.Obj2String(dataList.get(0).get("data")));
	    rangeMap.put("maxdata", SqlEngineHelper.Obj2String(dataList.get(1).get("data")));
		return rangeMap;
	}
	
	@Override
	public Object execute(JdbcTemplate jdbc) throws Exception {
		try{
			return jdbc.queryForList(sql.toString());
		}catch(Exception e){
			Throwable t = e;
			while (t != null) {
				if (t instanceof SQLException) {
					SQLException sqlException = (SQLException) t;
					SQLException nextException = sqlException
							.getNextException();
					if (nextException != null) {
						log.error("cause of " + nextException + ": ", e);
					}
				}
				t = t.getCause();
			}
			throw e; 
		}  
		
	}
	
	public void setCommandService(EDMCommandService commandService) {
		this.commandService = commandService;
	} 

}
