package com.orient.sysman.controller;

import com.orient.log.annotion.Action;
import com.orient.sysman.bean.*;
import com.orient.sysman.bussiness.RoleBusiness;
import com.orient.sysmodel.domain.role.Role;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2016/4/27.
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleBusiness roleBusiness;

    @Action(ownermodel = "系统管理-角色管理", detail = "查看角色信息")
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<RoleBean> list(Integer page, Integer limit) {
        List<RoleBean> roles = roleBusiness.findAll(page, limit);
        ExtGridData<RoleBean> retVal = new ExtGridData<>();
        retVal.setTotalProperty(roleBusiness.getRoleCount());
        retVal.setResults(roles);
        return retVal;
    }

    @Action(ownermodel = "系统管理-角色管理", detail = "新增角色信息")
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(Role role) {
        roleBusiness.create(role);
        CommonResponseData retVal = new CommonResponseData();
        retVal.setMsg("新增成功");
        return retVal;
    }

    @Action(ownermodel = "系统管理-角色管理", detail = "更新角色信息")
    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(Role role) {
        roleBusiness.update(role);
        CommonResponseData retVal = new CommonResponseData();
        retVal.setMsg("更新成功");
        return retVal;
    }

    @Action(ownermodel = "系统管理-角色管理", detail = "删除角色信息")
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(String toDelIds) {
        roleBusiness.delete(toDelIds);
        CommonResponseData retVal = new CommonResponseData();
        retVal.setMsg("删除成功");
        return retVal;
    }

    @Action(ownermodel = "系统管理-角色管理", detail = "查看角色信息")
    @RequestMapping("search")
    @ResponseBody
    public ExtGridData<RoleBean> search(Role role) {
        List<RoleBean> roleBeans = roleBusiness.search(role);
        ExtGridData<RoleBean> retVal = new ExtGridData<>();
        retVal.setTotalProperty(roleBeans.size());
        retVal.setResults(roleBeans);
        return retVal;
    }

    /**
     * @param roleId
     * @param assigned
     * @param page
     * @param limit
     * @return 获取角色相关schema
     */
    @Action(ownermodel = "系统管理-角色管理", detail = "查看Schema信息")
    @RequestMapping("listSchemas")
    @ResponseBody
    public ExtGridData<SchemaBean> listSchemas(String roleId, Boolean assigned, Integer page, Integer limit) {
        ExtGridData<SchemaBean> retVal = roleBusiness.listSchemas(roleId, assigned, page, limit);
        return retVal;
    }

    /**
     * @param roleId
     * @param assigned
     * @param page
     * @param limit
     * @return 获取角色相关用户
     */
    @Action(ownermodel = "系统管理-角色管理", detail = "查看角色相关用户信息")
    @RequestMapping("listUsers")
    @ResponseBody
    public ExtGridData<UserBean> listUsers(String roleId, Boolean assigned, Integer page, Integer limit, UserBean userFilter,String isSearch) {
        ExtGridData<UserBean> retVal = roleBusiness.listUsers(roleId, assigned, page, limit, userFilter,isSearch);
        return retVal;
    }

    /**
     * @param roleId
     * @param assigned
     * @param page
     * @param limit
     * @return 获取角色相关权限
     */
    @RequestMapping("listRights")
    @ResponseBody
    @Action(ownermodel = "系统管理-角色管理", detail = "查看角色权限信息")
    public ExtGridData<OperationBean> listRights(String roleId, Boolean assigned, Integer page, Integer limit) {
        ExtGridData<OperationBean> retVal = roleBusiness.listRights(roleId, assigned, page, limit);
        return retVal;
    }

    @RequestMapping("treeRoleFunctions")
    @ResponseBody
    @Action(ownermodel = "系统管理-角色管理", detail = "查看角色功能点信息")
    public AjaxResponseData<List<FuncBean>> treeRoleFunctions(String node, String roleId) {
        List<FuncBean> retVal = roleBusiness.treeRoleFunctions(node, roleId);
        roleBusiness.repairSchemaNodeChecked(retVal);
        return new AjaxResponseData<>(retVal);
    }

    /**
     * @param roleId      所属角色
     * @param selectedIds 所选数据
     * @param direction   移动方向
     * @return 分配用户权限
     */
    @RequestMapping("saveAssignRights")
    @ResponseBody
    @Action(ownermodel = "系统管理-角色管理", detail = "保存角色权限分配")
    public AjaxResponseData<Boolean> saveAssignRights(String roleId, String[] selectedIds, String direction) {
        AjaxResponseData<Boolean> retVal = new AjaxResponseData<>();
        retVal.setSuccess(roleBusiness.saveAssignRights(roleId, selectedIds, direction));
        return retVal;
    }

    @RequestMapping("saveAssignSchema")
    @ResponseBody
    @Action(ownermodel = "系统管理-角色管理", detail = "保存角色Schema分配")
    public AjaxResponseData<Boolean> saveAssignSchema(String roleId, String[] selectedIds, String direction) {
        AjaxResponseData<Boolean> retVal = new AjaxResponseData<>();
        retVal.setSuccess(roleBusiness.saveAssignSchema(roleId, selectedIds, direction));
        return retVal;
    }

    @RequestMapping("saveAssignUsers")
    @ResponseBody
    @Action(ownermodel = "系统管理-角色管理", detail = "保存角色用户分配")
    public AjaxResponseData<Boolean> saveAssignUsers(String roleId, String[] selectedIds, String direction) {
        AjaxResponseData<Boolean> retVal = new AjaxResponseData<>();
        retVal.setSuccess(roleBusiness.saveAssignUsers(roleId, selectedIds, direction));
        return retVal;
    }

    @RequestMapping("saveAssignFunctions")
    @ResponseBody
    @Action(ownermodel = "系统管理-角色管理", detail = "保存角色功能点分配")
    public AjaxResponseData<Boolean> saveAssignFunctions(String roleId, String[] functionIds) {
        AjaxResponseData<Boolean> retVal = new AjaxResponseData<>();
        retVal.setSuccess(roleBusiness.saveAssignFunctions(roleId, functionIds));
        return retVal;
    }

    @Action(ownermodel = "系统管理-角色管理", detail = "查看角色信息")
    @RequestMapping("listByIdFilter")
    @ResponseBody
    public List<RoleBean> listByIdFilter(String node, String ids) {
        return !"root".equals(node) ? roleBusiness.findAll(null,null) : roleBusiness.listByIdFilter(ids);
    }


    /**
     * @param roleId
     * @param assigned
     * @param page
     * @param limit
     * @return 获取角色相关权限
     */
    @RequestMapping("listPortals")
    @ResponseBody
    @Action(ownermodel = "系统管理-角色管理", detail = "查看角色磁贴信息")
    public ExtGridData<PortalBean> listPortals(String roleId, Boolean assigned, Integer page, Integer limit) {
        ExtGridData<PortalBean> retVal = roleBusiness.listPortals(roleId, assigned, page, limit);
        return retVal;
    }

    @RequestMapping("saveAssignPortals")
    @ResponseBody
    @Action(ownermodel = "系统管理-角色管理", detail = "保存角色磁贴分配")
    public AjaxResponseData<Boolean> saveAssignPortals(String roleId, Long[] selectedIds, String direction) {
        AjaxResponseData<Boolean> retVal = new AjaxResponseData<>();
        retVal.setSuccess(roleBusiness.saveAssignPortals(roleId, selectedIds,direction));
        return retVal;
    }

}
