/**
 * @Project: OrientEDM
 * @Title: SysModelServiceImpl.java
 * @Package com.orient.sqlengine.internal.svc
 * TODO
 * @author zhulc@cssrc.com.cn
 * @date Apr 17, 2012 3:19:24 PM
 * @Copyright: 2012 www.cssrc.com.cn. All rights reserved.
 * @version V1.0
 */


package com.orient.sqlengine.internal.svc;

import com.orient.metamodel.operationinterface.IRestriction;
import com.orient.sqlengine.api.ISysModelService;
import com.orient.sqlengine.cmd.api.EDMCommandService;
import com.orient.sqlengine.internal.sys.cmd.NumberUnitCmd;
import com.orient.sqlengine.internal.sys.cmd.RestrictionCmd;

import java.util.List;
import java.util.Map;

/**
 *
 * 系统模型服务的实现
 * @author zhulc@cssrc.com.cn
 * @date Apr 17, 2012
 */
public class SysModelJdbcServiceImpl implements ISysModelService {

    EDMCommandService commandService;

    /**
     * @param res
     * @return
     */
    @Override
    public Map<String, String> queryResDynamicRange(IRestriction res) {
        RestrictionCmd resCmd = new RestrictionCmd(res);
        resCmd.setCommandService(commandService);
        return resCmd.dynamicRange();
    }

    @Override
    public List<Map<String, Object>> queryNumberUnit() {
        NumberUnitCmd cmd = new NumberUnitCmd();
        cmd.setCommandService(commandService);
        return cmd.listUnit();
    }

    @Override
    public Map<String, Object> queryNumberUnitById(String id) {
        NumberUnitCmd cmd = new NumberUnitCmd();
        cmd.setCommandService(commandService);
        return cmd.queryById(id);
    }

    @Override
    public List<Map<String, Object>> queryNumberUnitByName(String name) {
        NumberUnitCmd cmd = new NumberUnitCmd();
        cmd.setCommandService(commandService);
        return cmd.queryByName(name);
    }

    public EDMCommandService getCommandService() {
        return commandService;
    }

    public void setCommandService(EDMCommandService commandService) {
        this.commandService = commandService;
    }

}
