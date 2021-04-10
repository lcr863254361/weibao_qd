package com.orient.sysmodel.service.user;

import java.util.List;
import java.util.Map;

import com.orient.sysmodel.domain.user.UserLoginHistory;
import com.orient.sysmodel.domain.user.UserLoginHistoryDAO;
import com.orient.utils.Commons;

/**
 * @author zhang yan
 * @Date 2012-3-15		下午04:28:55
 * @since Ver 1.1
 */
public class UserLoginHistoryServiceImpl implements UserLoginHistoryService {

    private UserLoginHistoryDAO dao;

    /**
     * TODO 新增用户登录信息
     *
     * @param loginHistory
     * @see com.orient.sysmodel.service.user.UserLoginHistoryService#createLoginHistory(com.orient.sysmodel.domain.user.UserLoginHistory)
     */

    public void createLoginHistory(UserLoginHistory loginHistory) {
        dao.save(loginHistory);
    }

    /**
     * 统计用户登录次数
     *
     * @param userName
     * @return
     */
    public String queryWebLoginTimes(String userName) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COUNT (*) ");
        sql.append("FROM UserLoginHistory ");
        sql.append(" WHERE userName = '" + userName + "' ");
        sql.append(" AND (opType='1' OR opType='2'");
        sql.append(" OR  opType='3' OR opType='4')");
        List resultList = dao.getHqlResult(sql.toString());
        return resultList.get(0).toString();
    }

    /**
     * 查找某操作类型的登录次数
     *
     * @param userName
     * @param optype
     * @return String
     */
    public String queryLoginTimesByOptype(String userName, String optype) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COUNT (*) ");
        sql.append("FROM UserLoginHistory ");
        sql.append(" WHERE userName = '" + userName + "' ");
        sql.append(" AND opType='" + optype + "' ");
        List resultList = dao.getHqlResult(sql.toString());
        return resultList.get(0).toString();
    }

    /**
     * 查询登录信息
     *
     * @param queryInfo
     * @return List<UserLoginHistory>
     */
    public List<UserLoginHistory> queryUserLoginHistory(UserLoginHistory queryInfo) {

        StringBuffer sql = new StringBuffer();
        sql.append("  FROM UserLoginHistory ");
        sql.append(" WHERE 1 = 1");
        /*if(!CommonTools.isNullString(queryInfo.getUserName())){
			sql.append(" AND userName like '%" + queryInfo.getUserName() + "%' ");
		}
		if(!CommonTools.isNullString(queryInfo.getUserDispalyname())){
			sql.append(" AND userDispalyName like '%" + queryInfo.getUserDispalyname() + "%' ");
		}
		if(!CommonTools.isNullString(queryInfo.getUserIp())){
			sql.append(" AND userIp = '"+queryInfo.getUserIp()+"' ");
		}
		if(!CommonTools.isNullString(queryInfo.getOpType())){
			sql.append(" AND opType = '"+queryInfo.getOpType()+"' ");
		}
		if(!CommonTools.isNullString(queryInfo.getSTART_TIME())){
			sql.append(" AND loginTime >= TO_DATE ('"+queryInfo.getSTART_TIME()+"', 'yyyy-mm-dd hh24:mi:ss') ");
		}
		if(!CommonTools.isNullString(queryInfo.getEND_TIME())){
			sql.append(" AND loginTime <= TO_DATE ('"+queryInfo.getEND_TIME()+"', 'yyyy-mm-dd hh24:mi:ss') ");
		}
		if(!CommonTools.isNullString(queryInfo.getUserDeptid())){
			sql.append(" AND dept.id = '"+queryInfo.getUserDeptid()+"' ");
		}*/
        sql.append(" AND (opType='1' OR opType='2'");
        sql.append(" OR  opType='3' OR opType='4')");
        sql.append(" ORDER BY loginTime DESC");
        List resultList = dao.getHqlResult(sql.toString());

        return resultList;
    }

    public void insert(UserLoginHistory map) {
        dao.save(map);
    }

    public UserLoginHistoryDAO getDao() {
        return dao;
    }

    public void setDao(UserLoginHistoryDAO dao) {
        this.dao = dao;
    }
	
	/*public void saveUserLogInfo(Map<String,String> userInfo, String type) {
		try {
			String OP_TYPE = (String) userInfo.get("OP_TYPE");
			String OP_MESSAGE = null;
			if (type.equals("rcp1")) {
				OP_MESSAGE = "Design Studio登录";
			} else if (type.equals("rcp2")) {
				OP_MESSAGE = "TBOM Studio登录";
			} else if (type.equals("rcp3")) {
				OP_MESSAGE = "ETL Studio登录";
			} else if (type.equals("rcp4")) {
				OP_MESSAGE = "WorkFlow Studio登录";
			} else {
				OP_MESSAGE= "未知系统";
			}
			UserLoginHistory userLoginHistory = new UserLoginHistory();
			userLoginHistory.setUserName(userInfo.get("USERNAME").toString());
			userLoginHistory.setUserDispalyname(userInfo.get("SHOWNAME").toString());
			userLoginHistory.setUserIp(userInfo.get("USER_IP").toString());
			userLoginHistory.setLoginTime(Commons.getSysDate());
			userLoginHistory.setOpType(OP_TYPE);
			userLoginHistory.setOpMessage(OP_MESSAGE);
			userLoginHistory.setUserDeptname(userInfo.get("DEPTNAME").toString());
			userLoginHistory.setUserDeptid(userInfo.get("DEPTID").toString());	
			dao.save(userLoginHistory);
		} catch (Exception exception) {
		}
	}*/

    /**
     * 记录用户登录客户端
     *
     * @param userLoginHistory
     */
    @Override
    public void saveUserLoginRecord(UserLoginHistory userLoginHistory) {
        try {
            dao.save(userLoginHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String queryCodeNum(String name, String password, String code) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COUNT (code)");
        sql.append("  FROM cwm_sys_function");
        sql.append(" WHERE functionid IN (");
        sql.append("          SELECT DISTINCT function_id");
        sql.append("                     FROM cwm_sys_role_function_tbom");
        sql.append("                    WHERE role_id IN (");
        sql.append("                             SELECT role_id");
        sql.append("                               FROM cwm_sys_role_user");
        sql.append("                              WHERE user_id =");
        sql.append("                                       (SELECT ID");
        sql.append("                                          FROM cwm_sys_user");
        sql.append("                                         WHERE user_name = '" + name + "'");
        sql.append("                                           AND PASSWORD = '" + password + "'");
        sql.append("                                           AND state = '1')))");
        sql.append("   AND code = '" + code + "'");
        List resultList = dao.getSqlResult(sql.toString());
        return resultList.get(0).toString();
    }

}

