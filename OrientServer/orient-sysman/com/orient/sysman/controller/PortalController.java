package com.orient.sysman.controller;

import com.orient.log.annotion.Action;
import com.orient.sysman.bussiness.PortalBusiness;
import com.orient.sysmodel.domain.sys.CwmPortalEntity;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Portal管理
 *
 * @author enjoy
 * @createTime 2016-06-02 10:54
 */
@Controller
@RequestMapping("/protal")
public class PortalController {

    @Autowired
    PortalBusiness portalBusiness;

    @Action(ownermodel = "系统管理-磁贴管理", detail = "查看磁贴信息")
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CwmPortalEntity> list(Integer page, Integer limit, CwmPortalEntity filter) {
        return portalBusiness.list(page, limit, filter);
    }

    /**
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @Action(ownermodel = "系统管理-磁贴管理", detail = "删除磁贴信息")
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(Long[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        portalBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @Action(ownermodel = "系统管理-磁贴管理", detail = "新增磁贴信息")
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CwmPortalEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        portalBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @Action(ownermodel = "系统管理-磁贴管理", detail = "更新磁贴信息")
    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CwmPortalEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        portalBusiness.update(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }
}
