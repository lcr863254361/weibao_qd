package com.orient.sysman.controller;

import com.orient.log.annotion.Action;
import com.orient.sysman.bean.UserToolBean;
import com.orient.sysman.bussiness.UserToolBusiness;
import com.orient.sysmodel.domain.sys.CwmSysToolsEntity;
import com.orient.sysmodel.domain.tools.CwmUserTool;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 工具管理
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/userTool")
public class UserToolController {
    @Autowired
    UserToolBusiness userToolBusiness;

    @Action(ownermodel = "系统管理-自定义工具管理", detail = "查看用户自定义工具信息")
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<UserToolBean> list(CwmSysToolsEntity filter) {
        ExtGridData<UserToolBean> retVal = new ExtGridData<>();
        Long userId = Long.valueOf(UserContextUtil.getUserId());
        List<UserToolBean> tools = userToolBusiness.getUserToolsByFilter(filter, userId);
        retVal.setResults(tools);
        retVal.setTotalProperty(tools.size());
        return retVal;
    }

    @RequestMapping("listUncreatedTools")
    @ResponseBody
    public ExtGridData<CwmSysToolsEntity> listUncreatedTools(Long toolGroupId) {
        ExtGridData<CwmSysToolsEntity> retVal = new ExtGridData<>();
        Long userId = Long.valueOf(UserContextUtil.getUserId());
        List<CwmSysToolsEntity> tools = userToolBusiness.getUncreatedUserToolsByGroupId(toolGroupId, userId);
        retVal.setResults(tools);
        retVal.setTotalProperty(tools.size());
        return retVal;
    }

    @Action(ownermodel = "系统管理-自定义工具管理", detail = "新增自定义工具")
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(@RequestBody ArrayList<Long> toolIds) {
        CommonResponseData retVal = new CommonResponseData();
        Long userId = Long.valueOf(UserContextUtil.getUserId());
        if(toolIds != null) {
            userToolBusiness.createUserTools(toolIds, userId);
            retVal.setSuccess(true);
            retVal.setMsg("保存成功!");
        }
        else {
            retVal.setSuccess(false);
            retVal.setMsg("未选择需要添加的工具!");
        }
        return retVal;
    }

    @Action(ownermodel = "系统管理-自定义工具管理", detail = "更新自定义工具信息")
    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CwmUserTool userTool) {
        CommonResponseData retVal = new CommonResponseData();
        userToolBusiness.updateUserToolPath(userTool);
        retVal.setMsg("保存成功!");
        return retVal;
    }

    /**
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @Action(ownermodel = "系统管理-自定义工具管理", detail = "删除自定义工具信息")
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(Long[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        userToolBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * 获取用户工具路径
     */
    @Action(ownermodel = "系统管理-用户工具", detail = "获取用户工具路径")
    @RequestMapping("getUserToolPathByName")
    @ResponseBody
    public AjaxResponseData<String> getUserToolPathByName(String toolName) {
        AjaxResponseData<String> retVal = new AjaxResponseData<>();
        String userId = UserContextUtil.getUserId();
        String toolPath = userToolBusiness.getUserToolPathByName(userId, toolName);
        retVal.setResults(toolPath);
        return retVal;
    }
}
