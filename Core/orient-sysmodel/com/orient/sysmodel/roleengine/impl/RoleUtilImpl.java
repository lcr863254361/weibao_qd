/**
 * RoleUtilImpl.java
 * com.orient.sysmodel.roleengine.impl
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2012-4-1 		zhang yan
 * <p>
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.orient.sysmodel.roleengine.impl;

import com.orient.sysmodel.domain.role.RoleModel;
import com.orient.sysmodel.operationinterface.IRoleModel;
import com.orient.sysmodel.roleengine.IRoleUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * ClassName:RoleUtilImpl
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author zhang yan
 * @Date 2012-4-1		下午03:21:12
 * @see
 * @since Ver 1.1
 */

public class RoleUtilImpl implements IRoleUtil {

    private RoleModel roleModel = null;

    /**
     * @param reset
     * @return RoleModel
     * @throws
     * @Method: getRoleModel
     * <p>
     * 初始化角色数据模型，即实例化角色数据模型
     */
    public IRoleModel getRoleModel(boolean reset) {
        if (roleModel == null || reset) {
            if (!initRoleModel()) {
                return null;
            } else {
                return roleModel;
            }
        } else {
            return roleModel;
        }
    }

    /**
     * @return boolean
     * @throws
     * @Method: initRoleModel
     * <p>
     * 获取角色数据模型
     */
    public boolean initRoleModel() {
        roleModel = new RoleModel();
        return roleModel.initRoleModel();
    }

    /**
     * 程序启动后就运行,初始化系统模型
     *
     * @param contextLoad context上下文
     * @return
     */
    @Override
    public boolean modelLoadRun(WebApplicationContext contextLoad) {
        this.getRoleModel(true);
        return true;
    }

}

