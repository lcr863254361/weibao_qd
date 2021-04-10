package com.orient.collab.controller;

/**
 * the team controller
 *
 * @author Seraph
 *         2016-07-08 下午3:41
 */
import com.orient.collab.business.TeamBusiness;
import com.orient.collab.model.AssignUserBean;
import com.orient.collab.model.RoleFunctionTreeNode;
import com.orient.collab.model.RoleUserGridModel;
import com.orient.collab.model.RoleUserTreeNode;
import com.orient.sysmodel.domain.collab.CollabFunction;
import com.orient.sysmodel.domain.collab.CollabRole;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/collabTeam")
public class TeamController extends BaseController {

    @RequestMapping(value = "/roles")
    @ResponseBody
    public AjaxResponseData<List<CollabRole>> getRoles(String modelName, String dataId){
        return new AjaxResponseData(teamBusiness.getRoles(modelName, dataId));
    }

    @RequestMapping(value = "/addRole")
    @ResponseBody
    public CommonResponseData addRole(String modelName, String dataId, String name){
        return teamBusiness.addRole(modelName, dataId, name);
    }

    @RequestMapping(value = "/modifyRole")
    @ResponseBody
    public CommonResponseData modifyRole(String roleId, String oldRoleName, String name){
        return teamBusiness.modifyRole(roleId, oldRoleName, name);
    }

    @RequestMapping(value = "/deleteRole")
    @ResponseBody
    public CommonResponseData deleteRole(String roleId){
        return this.teamBusiness.deleteRole(roleId);
    }

    @RequestMapping(value = "/user/current/functions")
    @ResponseBody
    public AjaxResponseData<List<CollabFunction>> getCurrentUserFunctions(String modelName, String dataId){
        return new AjaxResponseData(teamBusiness.getCurrentUserFunctions(modelName, dataId));
    }

    @RequestMapping(value = "/role/{roleId}/functions")
    @ResponseBody
    public AjaxResponseData<List<CollabFunction>> getRoleFunctions(@PathVariable(value = "roleId") String roleId){
        return new AjaxResponseData(teamBusiness.getRoleFunctions(roleId));
    }

    @RequestMapping(value = "/model/{modelName}/functions")
    @ResponseBody
    public AjaxResponseData<List<CollabFunction>> getModelFunctions(@PathVariable(value = "modelName") String modelName){
        return new AjaxResponseData(teamBusiness.getModelFunctions(modelName));
    }

    @RequestMapping("/usersToAssign")
    @ResponseBody
    public ExtGridData<AssignUserBean> getUsersToAssign(String roleId, boolean assigned, int page, int limit, AssignUserBean userFilter) {
        return teamBusiness.getUsersToAssign(roleId, assigned, page, limit, userFilter);
    }

    @RequestMapping("/saveAssignedUsers")
    @ResponseBody
    public AjaxResponseData<Boolean> saveAssignedUsers(String roleId, String[] selectedIds, String direction) {
        AjaxResponseData<Boolean> retVal = new AjaxResponseData<>();

        StringBuilder errorMsg = new StringBuilder();
        retVal.setSuccess(teamBusiness.saveAssignedUsers(roleId, selectedIds, direction, errorMsg));
        retVal.setMsg(errorMsg.toString());
        return retVal;
    }
    @RequestMapping("/saveAssignedUsersByRolename")
    @ResponseBody
    public AjaxResponseData<Boolean> saveAssignedUsersByRolename(String roleName, String modelName, String dataId,String[] selectedIds,String taskName) {
        AjaxResponseData<Boolean>  retVal = new AjaxResponseData<>();
        StringBuilder errorMsg = new StringBuilder();
        retVal.setSuccess(teamBusiness.saveAssignedUsersByRolename(roleName, modelName, dataId,selectedIds,taskName,errorMsg));
        retVal.setMsg(errorMsg.toString());
        return retVal;
    }

    @RequestMapping(value = "/roleUsers/grid")
    @ResponseBody
    public AjaxResponseData<List<RoleUserGridModel>> getRoleUserGridModels(String modelName, String dataId){
        return new AjaxResponseData(teamBusiness.getRoleUserGridModels(modelName, dataId));
    }

    @RequestMapping(value = "/roleUsers/tree")
    @ResponseBody
    public AjaxResponseData<List<RoleUserTreeNode>> getRoleUserTreeModels(String modelName, String dataId, String parId){
        return new AjaxResponseData(teamBusiness.getRoleUserTreeModels(modelName, dataId, parId));
    }

    @RequestMapping(value = "/roleFunctionTreeNodes")
    @ResponseBody
    public AjaxResponseData<List<RoleFunctionTreeNode>> getRoleFunctionTreeNodes(String modelName, String roleId){
        return new AjaxResponseData(teamBusiness.getRoleFunctionTreeNodes(modelName, roleId));
    }

    @RequestMapping(value = "/saveAssignFunctions")
    @ResponseBody
    public CommonResponseData saveAssignFunctions(String roleId, String functionIds){
        return teamBusiness.saveAssignFunctions(roleId, functionIds);
    }

    @Autowired
    private TeamBusiness teamBusiness;
}
