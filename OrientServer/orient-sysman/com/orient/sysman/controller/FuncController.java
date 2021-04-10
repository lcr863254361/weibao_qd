package com.orient.sysman.controller;

import com.orient.log.annotion.Action;
import com.orient.sysman.bean.FuncBean;
import com.orient.sysman.bussiness.FuncBusiness;
import com.orient.sysmodel.domain.role.Function;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/21.
 */
@Controller
@RequestMapping("/func")
public class FuncController {
    @Autowired
    FuncBusiness funcBusiness;

    @Action(ownermodel = "系统管理-功能点管理", detail = "查看功能点信息")
    @RequestMapping("getByPid")
    @ResponseBody
    public AjaxResponseData<List<FuncBean>> getByPid(String node) {
        return new AjaxResponseData<>(funcBusiness.findByPid(node));
    }

    @Action(ownermodel = "系统管理-功能点管理", detail = "新增功能点信息")
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(Function func) {
        funcBusiness.create(func);
        CommonResponseData retVal = new CommonResponseData();
        retVal.setMsg("新增成功");
        return retVal;
    }

    @Action(ownermodel = "系统管理-功能点管理", detail = "修改功能点信息")
    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(Function func) {
        funcBusiness.update(func);

        CommonResponseData retVal = new CommonResponseData();
        retVal.setMsg("更新成功");
        return retVal;
    }

    @Action(ownermodel = "系统管理-功能点管理", detail = "删除功能点信息")
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(String toDelIds) {
        funcBusiness.delete(toDelIds);

        CommonResponseData retVal = new CommonResponseData();
        retVal.setMsg("删除成功");
        return retVal;
    }

    @Action(ownermodel = "系统管理-功能点管理", detail = "查看系统功能点图标")
    @RequestMapping("getFunImages")
    @ResponseBody
    public List<Map<String, String>> getFunImages() {
        List<Map<String, String>> retVal = funcBusiness.getFunImages();
        return retVal;
    }


    /**
     * @return 返回第一层用户功能点描述
     */
    @RequestMapping("getFirstLevelFunction")
    @ResponseBody
    public AjaxResponseData<List<FuncBean>> getFirstLevelFunction() {
        return new AjaxResponseData<>(funcBusiness.getFirstLevelFunction());
    }

    /**
     * @param node 父节点id
     * @return 子节点描述
     */
    @RequestMapping("getSubFunctions")
    @ResponseBody
    public AjaxResponseData<List<FuncBean>> getSubFunctions(String node) {
        return new AjaxResponseData<>(funcBusiness.getSubFunctions(node));
    }

}