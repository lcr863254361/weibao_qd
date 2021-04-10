/**
 * FunctionServiceImpl.java
 * com.sysmodel.service.role
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2012-3-20 		zhang yan
 * <p>
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.orient.sysmodel.service.role;

import com.orient.sysmodel.domain.BaseBean;
import com.orient.sysmodel.domain.role.Function;
import com.orient.sysmodel.domain.role.FunctionDAO;
import com.orient.sysmodel.domain.role.RoleFunctionTbom;
import com.orient.sysmodel.domain.role.RoleFunctionTbomDAO;
import com.orient.sysmodel.operationinterface.IRoleModel;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.utils.CommonTools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ClassName:FunctionServiceImpl
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author zhang yan
 * @Date 2012-3-20		上午11:12:04
 * @see
 * @since Ver 1.1
 */
public class FunctionServiceImpl extends BaseBean implements FunctionService {

    private FunctionDAO dao;
    private RoleFunctionTbomDAO roleFunctionTbomDao;
    private IRoleUtil roleEngine;


    /**
     * dao
     *
     * @return the dao
     * @since CodingExample Ver 1.0
     */

    public FunctionDAO getDao() {
        return dao;
    }

    /**
     * dao
     *
     * @param dao the dao to set
     * @since CodingExample Ver 1.0
     */

    public void setDao(FunctionDAO dao) {
        this.dao = dao;
    }

    /**
     * roleFunctionTbomDao
     *
     * @return the roleFunctionTbomDao
     * @since CodingExample Ver 1.0
     */

    public RoleFunctionTbomDAO getRoleFunctionTbomDao() {
        return roleFunctionTbomDao;
    }

    /**
     * roleFunctionTbomDao
     *
     * @param roleFunctionTbomDao the roleFunctionTbomDao to set
     * @since CodingExample Ver 1.0
     */

    public void setRoleFunctionTbomDao(RoleFunctionTbomDAO roleFunctionTbomDao) {
        this.roleFunctionTbomDao = roleFunctionTbomDao;
    }


    /**
     * @return the roleEngine
     */
    public IRoleUtil getRoleEngine() {
        return roleEngine;
    }

    /**
     * @param roleEngine the roleEngine to set
     */
    public void setRoleEngine(IRoleUtil roleEngine) {
        this.roleEngine = roleEngine;
    }

    /**
     * 查找功能点
     *
     * @param code
     * @return Function
     * @throws
     * @Method: findByCode
     * <p>
     * TODO
     */
    public Function findByCode(String code) {
        List<Function> list = dao.findByCode(code);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public Function findByProperty(String propertyName, Object value) {

        List<Function> list = dao.findByProperty(propertyName, value);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 查找所有显示的功能点
     *
     * @return
     * @Method: findAllShowFunction
     * <p>
     * TODO
     * @see com.orient.sysmodel.service.role.FunctionService#findAllShowFunction()
     */
    public List<Function> findAllShowFunction() {
        return dao.findByIsShow(Long.valueOf(1));
    }

    /**
     * 查找所有功能点
     *
     * @return List<Function>
     * @throws
     * @Method: findAllFunction
     * <p>
     * TODO
     */
    public List<Function> findAllFunction() {
        return dao.findAll();
    }

    /**
     * 查找功能点
     *
     * @param functionId
     * @return Function
     * @throws
     * @Method: findById
     * <p>
     * TODO
     */
    public Function findById(String functionId) {
        Map<String, Function> localFunctions = roleEngine.getRoleModel(false).getFunctions();
        if (localFunctions.values().stream().filter(function -> function.getFunctionid().equals(Long.valueOf(functionId))).count() > 0) {
            return localFunctions.values().stream().filter(function -> function.getFunctionid().equals(Long.valueOf(functionId))).findFirst().get();
        } else
            return null;

    }

    public List<Function> findByPid(Long pid) {
        Map<String, Function> localFunctions = roleEngine.getRoleModel(false).getFunctions();
        List<Function> retVal = new ArrayList<>();
        localFunctions.forEach((functionId, function) -> {
            if (null != function.getParentid() && function.getParentid().equals(pid)) {
                retVal.add(function);
            }
        });
        return retVal;
    }

    /**
     * 新增功能点信息
     *
     * @param function
     * @return void
     * @throws
     * @Method: createFunction
     * <p>
     * TODO
     */
    public void createFunction(Function function) {
        dao.save(function);
        Map<String, Function> functionMap = roleEngine.getRoleModel(false).getFunctions();
        functionMap.put(function.getFunctionid().toString(), function);
        Long pid = function.getParentid();
        if(pid!=null && !"".equals(pid)) {
            Function pf = functionMap.get(pid.toString());
            pf.getChildrenFunction().add(function);
        }
    }

    /**
     * 更新功能点信息
     *
     * @param function
     * @return void
     * @throws
     * @Method: updateFunction
     * <p>
     * TODO
     */
    @SuppressWarnings("rawtypes")
    public void updateFunction(Function function, boolean isUpdate) {
        dao.attachDirty(function);

		/*更新角色*/
        if (isUpdate) {
            for (Iterator it = function.getRoleFunctionTboms().iterator(); it.hasNext(); ) {
                RoleFunctionTbom funTbomRole = (RoleFunctionTbom) it.next();

                if (!CommonTools.isNullString(funTbomRole.getTbomDir().getId())) {

                    funTbomRole.getRole().deleteRoleFunTbom(funTbomRole.getId());

                    funTbomRole.getTbomDir().deleteRoleFunTbom(funTbomRole.getId());

                    roleFunctionTbomDao.delete(funTbomRole);

                    it.remove();
                }
            }

            roleEngine.getRoleModel(false).initRoleTboms();

        }

    }

    /**
     * 删除功能点及相关权限信息
     *
     * @param functionId
     * @return void
     * @throws
     * @Method: deleteFunction
     * <p>
     * TODO
     */
    public void deleteFunction(String functionId) {
        Function function = dao.findById(Long.valueOf(functionId));
        this.deleteFunction(function);
    }

    /**
     * 删除功能点及相关权限信息
     *
     * @param function
     * @return void
     * @throws
     * @Method: deleteFunction
     * <p>
     * TODO
     */
    @SuppressWarnings("rawtypes")
    public void deleteFunction(Function function) {
        IRoleModel roleModel = roleEngine.getRoleModel(false);
        //删除功能子节点
        for (Iterator it = function.getChildrenFunction().iterator(); it.hasNext(); ) {
            Function childFunction = (Function) it.next();
            deleteFunction(childFunction);
        }
        //Tbom角色功能信息roleFunctionTbom
        for (Iterator it = function.getRoleFunctionTboms().iterator(); it.hasNext(); ) {
            RoleFunctionTbom roleFunctionTbom = (RoleFunctionTbom) it.next();
            roleFunctionTbomDao.delete(roleFunctionTbom);
            roleFunctionTbom.getRole().deleteRoleFunTbom(roleFunctionTbom.getId());
            roleFunctionTbom.getTbomDir().deleteRoleFunTbom(roleFunctionTbom.getId());

        }
        //删除功能点
        dao.delete(function);
        roleModel.getFunctions().remove(function.getFunctionid().toString());

    }
}

