package com.orient.sysmodel.domain;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sysmodel.domain.role.Role;
import com.orient.sysmodel.operationinterface.IRoleModel;
import com.orient.sysmodel.roleengine.IRoleUtil;

/**
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public class BaseBean {

    /**
     * 获取POJO的对象
     *
     * @param beanName 要获取POJO的名称
     * @return Object
     */
    public Object getBean(String beanName) {
        return OrientContextLoaderListener.Appwac.getBean(beanName);
    }

    public RoleDAOFactory getRoleDAOFactory() {
        RoleDAOFactory roleDAOFactoy = (RoleDAOFactory) getBean("roledaofactory");
        return roleDAOFactoy;
    }

    public UserDAOFactory getUserDAOFactory() {
        UserDAOFactory userDAOFactoy = (UserDAOFactory) getBean("userdaofactory");
        return userDAOFactoy;
    }

    public TbomDAOFactory getTbomDAOFactory() {
        TbomDAOFactory tbomDAOFactoy = (TbomDAOFactory) getBean("tbomdaofactory");
        return tbomDAOFactoy;
    }

    public SysDAOFactory getSysDAOFactory() {
        SysDAOFactory sysDAOFactory = (SysDAOFactory) getBean("sysdaofactory");
        return sysDAOFactory;
    }

    public void updateRoleModel(Role role) {
        //更新内存信息
        IRoleUtil engine = (IRoleUtil) getBean("RoleEngine");
        IRoleModel roleModel = engine.getRoleModel(false);
        roleModel.getRoles().remove(role.getId());
        roleModel.getRoles().put(role.getId(), role);
    }

}
