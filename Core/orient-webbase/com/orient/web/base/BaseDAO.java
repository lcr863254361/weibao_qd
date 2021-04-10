package com.orient.web.base;

import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.roleengine.IRoleUtil;
import org.jbpm.api.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDAO {

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    protected IBusinessModelService businessModelService;

    @Autowired
    protected IRoleUtil roleEngine;

    @Autowired
    protected MetaDAOFactory metaDaoFactory;

    @Autowired
    protected ProcessEngine processEngine;

    public String getUserAllNameFromUserName(String userName) {
        String allName = "";
        if (!"".equals(userName)) {
            String[] userNames = userName.split(",");
            for (String str : userNames) {
                allName += roleEngine.getRoleModel(false).getUserByUserName(str).getAllName() + ",";
            }
            if (allName.length() > 0) {
                //去除多余的逗号
                allName = allName.substring(0, allName.length() - 1);
            }
        }
        return allName;
    }


    public String getUserLoginNameByUserId(String userId) {
        return roleEngine.getRoleModel(false).getUserById(userId).getUserName();
    }

}
