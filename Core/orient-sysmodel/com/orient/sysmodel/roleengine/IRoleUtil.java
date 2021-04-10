package com.orient.sysmodel.roleengine;

import com.orient.edm.init.IContextLoadRun;
import com.orient.sysmodel.operationinterface.IRoleModel;

/**
 * @author zhang yan
 * @Date 2012-4-1		下午03:20:15
 */
public interface IRoleUtil extends IContextLoadRun {

    /**
     * 初始化角色数据模型，即实例化角色数据模型
     *
     * @param reset --是否需要重新初始化: true 需要
     * @return RoleModel
     */
    IRoleModel getRoleModel(boolean reset);

    /**
     * 获取角色数据模型
     *
     * @return boolean
     */
    boolean initRoleModel();

}

