/**
 * 
 */
package com.orient.sysmodel.dao.flow;

import java.util.List;
import java.util.Map;

import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.orient.utils.CommonTools;

/**
 * simple introduction.
 *
 * <p>detailed commentProcessInstanceDAO</p>
 * @author [创建人]  mengbin <br/> 
 * 		   [创建时间] 2016-1-12 下午02:25:14 <br/> 
 * 		   [修改人] mengbin <br/>
 * 		   [修改时间] 2016-1-12 下午02:25:14
 * @see
 */
@Repository
public class ProcessInstanceDAO{

	@Autowired
	protected MetaDAOFactory metaDaoFactory;
	
	/**
	 * 根据历史流程Id，获取当前流程的Id。
	 * 该方法主要用于查询，主流程还没有结束，子流程的已经结束的情况
	 * 
	 * @author [创建人] spf <br/>
	 *         [创建时间] 2014-6-27 下午2:08:31 <br/>
	 *         [修改人] spf <br/>
	 *         [修改时间] 2014-6-27 下午2:08:31
	 * @param hisExecutionId
	 * @return
	 * @see
	 */
	public String getPrcInstIdByHisExecution(String hisExecutionId) {
		String prcInstId = hisExecutionId;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT SUP_EXC_ID_ FROM JBPM4_FLOW_BRANCH_RELATION WHERE SUB_EXC_ID_ = ?");
			List<Map<String, Object>> list = metaDaoFactory.getJdbcTemplate()
					.queryForList(sql.toString(),
							new Object[] { hisExecutionId });
			if (null != list && list.size() > 0) {
				prcInstId = CommonTools.Obj2String(list.get(0).get(
						"SUP_EXC_ID_"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prcInstId;
	}
}

