package com.orient.sysman.controller;

import com.orient.log.annotion.Action;
import com.orient.sysman.bussiness.ToolGroupBusiness;
import com.orient.sysmodel.domain.sys.CwmSysToolsGroupEntity;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 工具分组管理
 *
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/ToolGroup")
public class ToolGroupController {
    @Autowired
    ToolGroupBusiness toolGroupBusiness;


    @Action(ownermodel = "系统管理-工具分组管理", detail = "查看系统工具分组信息")
    @RequestMapping("list")
    @ResponseBody
    public AjaxResponseData<List<CwmSysToolsGroupEntity>> list(String node, Integer page, Integer limit, CwmSysToolsGroupEntity filter) {
        return new AjaxResponseData(toolGroupBusiness.list(page, limit, filter, node));
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @Action(ownermodel = "系统管理-工具管理", detail = "创建系统工具分组信息")
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CwmSysToolsGroupEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        toolGroupBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @Action(ownermodel = "系统管理-工具管理", detail = "更新系统工具分组信息")
    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CwmSysToolsGroupEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        toolGroupBusiness.update(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    /**
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @Action(ownermodel = "系统管理-工具管理", detail = "删除系统工具分组信息")
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(Long[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        toolGroupBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

}
