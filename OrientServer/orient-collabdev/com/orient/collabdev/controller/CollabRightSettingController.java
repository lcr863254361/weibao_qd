package com.orient.collabdev.controller;

import com.orient.collab.model.AssignUserBean;
import com.orient.collab.model.RoleFunctionTreeNode;
import com.orient.collab.model.RoleUserTreeNode;
import com.orient.collabdev.business.designing.CollabRightSetttingBusiness;
import com.orient.sysmodel.domain.collab.CollabFunction;
import com.orient.sysmodel.domain.collab.CollabRole;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/collabRightSetting")
public class CollabRightSettingController extends BaseController {

    @RequestMapping(value = "/roles")
    @ResponseBody
    public AjaxResponseData<List<CollabRole>> getRoles(String nodeId) {
        return new AjaxResponseData(collabRightSetttingBusiness.getRoles(nodeId));
    }

    @RequestMapping(value = "/addRole")
    @ResponseBody
    public CommonResponseData addRole(String nodeId, String name) {
        return collabRightSetttingBusiness.addRole(nodeId, name);
    }

    @RequestMapping(value = "/modifyRole")
    @ResponseBody
    public CommonResponseData modifyRole(String roleId, String oldRoleName, String name) {
        return collabRightSetttingBusiness.modifyRole(roleId, oldRoleName, name);
    }

    @RequestMapping(value = "/deleteRole")
    @ResponseBody
    public CommonResponseData deleteRole(String roleId) {
        return this.collabRightSetttingBusiness.deleteRole(roleId);
    }

    @RequestMapping(value = "/user/current/functions")
    @ResponseBody
    public AjaxResponseData<List<CollabFunction>> getCurrentUserFunctions(String nodeId) {
        return new AjaxResponseData(collabRightSetttingBusiness.getCurrentUserFunctions(nodeId));
    }

    @RequestMapping(value = "/role/{roleId}/functions")
    @ResponseBody
    public AjaxResponseData<List<CollabFunction>> getRoleFunctions(@PathVariable(value = "roleId") String roleId) {
        return new AjaxResponseData(collabRightSetttingBusiness.getRoleFunctions(roleId));
    }

    @RequestMapping("/usersToAssign")
    @ResponseBody
    public ExtGridData<AssignUserBean> getUsersToAssign(String roleId, boolean assigned, int page, int limit, AssignUserBean userFilter) {
        return collabRightSetttingBusiness.getUsersToAssign(roleId, assigned, page, limit, userFilter);
    }

    @RequestMapping("/saveAssignedUsers")
    @ResponseBody
    public AjaxResponseData<Boolean> saveAssignedUsers(String roleId, String[] selectedIds, String direction) {
        AjaxResponseData<Boolean> retVal = new AjaxResponseData<>();
        StringBuilder errorMsg = new StringBuilder();
        retVal.setSuccess(collabRightSetttingBusiness.saveAssignedUsers(roleId, selectedIds, direction, errorMsg));
        retVal.setMsg(errorMsg.toString());
        return retVal;
    }

    @RequestMapping(value = "/roleUsers/tree")
    @ResponseBody
    public AjaxResponseData<List<RoleUserTreeNode>> getRoleUserTreeModels(String nodeId, String parId) {
        return new AjaxResponseData(collabRightSetttingBusiness.getRoleUserTreeModels(nodeId, parId));
    }

    @RequestMapping(value = "/roleFunctionTreeNodes")
    @ResponseBody
    public AjaxResponseData<List<RoleFunctionTreeNode>> getRoleFunctionTreeNodes(String roleId, String node) {
        return new AjaxResponseData(collabRightSetttingBusiness.getRoleFunctionTreeNodes(roleId, node));
    }

    @RequestMapping(value = "/saveAssignFunctions")
    @ResponseBody
    public CommonResponseData saveAssignFunctions(String roleId, String functionIds) {
        return collabRightSetttingBusiness.saveAssignFunctions(roleId, functionIds);
    }

    @Autowired
    private CollabRightSetttingBusiness collabRightSetttingBusiness;
}
