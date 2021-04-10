/**
 * 
 */
package com.orient.sysmodel.dao.flow;

import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.utils.CommonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * simple introduction.
 *
 * <p>detailed commentFlowTaskDAO</p>
 * @author [创建人]  mengbin <br/> 
 * 		   [创建时间] 2016-1-12 下午02:28:25 <br/> 
 * 		   [修改人] mengbin <br/>
 * 		   [修改时间] 2016-1-12 下午02:28:25
 * @see
 */
@Repository
public class FlowTaskDAO{

	@Autowired
	protected MetaDAOFactory metaDaoFactory;

	/**
	 * 通过历史任务DBid，获取流程任务Id
	 *
	 * <p>getFlowTaskIdByHisActivityDBId</p>
	 * @author [创建人]  mengbin <br/> 
	 * 		   [创建时间] 2016-1-12 上午11:30:31 <br/> 
	 * 		   [修改人] mengbin <br/>
	 * 		   [修改时间] 2016-1-12 上午11:30:31
	 * @param activityHisDBId
	 * @return
	 * String
	 * @see
	 */
	public String getFlowTaskIdByHisActivityDBId(String activityHisDBId) {
				
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TO_CHAR(HTASK_) FLOWTASKID FROM JBPM4_HIST_ACTINST WHERE DBID_ = ").append(activityHisDBId);
		String result = CommonTools.Obj2String(metaDaoFactory.getJdbcTemplate().queryForMap(sql.toString()).get("FLOWTASKID"));
		return result;
	}

	/**
	 * 从历史任务id获取历史任务名称
	 * @param hisTaskId
	 * @return
	 */
	public String getFlowTaskNameByHisTaskId(String hisTaskId) {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TO_CHAR(ACTIVITY_NAME_) TASKNAME FROM JBPM4_HIST_ACTINST WHERE HTASK_ = ").append(hisTaskId);
		String result = CommonTools.Obj2String(metaDaoFactory.getJdbcTemplate().queryForMap(sql.toString()).get("TASKNAME"));
		return result;
	}

	public String getFlowTaskIdByHisTaskId(String hisTaskId) {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TO_CHAR(DBID_) ACTIVITID FROM JBPM4_HIST_ACTINST WHERE HTASK_ = ").append(hisTaskId);
		String result = CommonTools.Obj2String(metaDaoFactory.getJdbcTemplate().queryForMap(sql.toString()).get("ACTIVITID"));
		return result;
	}


	public String getSuperTaskIdByHisTaskDBId(String hisTaskDBId) {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TO_CHAR(SUPERTASK_) SUPERTASKID FROM JBPM4_HIST_TASK WHERE DBID_ = ").append(hisTaskDBId);
		String result = CommonTools.Obj2String(metaDaoFactory.getJdbcTemplate().queryForMap(sql.toString()).get("SUPERTASKID"));
		return result;
	}
}
