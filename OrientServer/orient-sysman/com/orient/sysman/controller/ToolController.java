package com.orient.sysman.controller;

import com.orient.log.annotion.Action;
import com.orient.sysman.bussiness.ToolBusiness;
import com.orient.sysmodel.domain.sys.CwmSysToolsEntity;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 工具管理
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/Tool")
public class ToolController {
    @Autowired
    ToolBusiness toolBusiness;

    @Action(ownermodel = "系统管理-工具管理", detail = "查看系统工具信息")
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CwmSysToolsEntity> list(Integer page, Integer limit, CwmSysToolsEntity filter) {
        return toolBusiness.list(page, limit, filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @Action(ownermodel = "系统管理-工具管理", detail = "新增系统工具信息")
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CwmSysToolsEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        toolBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @Action(ownermodel = "系统管理-工具管理", detail = "更新系统工具信息")
    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CwmSysToolsEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        toolBusiness.update(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    /**
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @Action(ownermodel = "系统管理-工具管理", detail = "删除系统工具信息")
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(Long[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        toolBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }
}
