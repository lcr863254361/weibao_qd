package com.orient.sysman.controller;

import com.orient.log.annotion.Action;
import com.orient.sysman.bean.FileGroupWrapper;
import com.orient.sysman.bussiness.FileGroupBusiness;
import com.orient.utils.JsonUtil;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 文件分组Controller
 *
 * @author enjoy
 * @creare 2016-04-29 8:54
 */
@Controller
@RequestMapping("/fileGroup")
public class FileGroupController {

    @Autowired
    FileGroupBusiness fileGroupBusiness;

    @Action(ownermodel = "系统管理-文件分组管理", detail = "查看文件分组信息")
    @RequestMapping("listByPiId")
    @ResponseBody
    public AjaxResponseData<List<FileGroupWrapper>> listByPiId(String node, Integer isCustomer) {
        return new AjaxResponseData<>(fileGroupBusiness.listByPiId(node, isCustomer));
    }

    @Action(ownermodel = "系统管理-文件分组管理", detail = "新增文件分组")
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(String formData) {
        List<FileGroupWrapper> toSavenData = JsonUtil.getJavaCollection(new FileGroupWrapper(), formData);
        CommonResponseData retVal = fileGroupBusiness.create(toSavenData);
        return retVal;
    }

    @Action(ownermodel = "系统管理-文件分组管理", detail = "更新文件分组信息")
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(String formData) {
        List<FileGroupWrapper> toDeleteData = JsonUtil.getJavaCollection(new FileGroupWrapper(), formData);
        CommonResponseData retVal = fileGroupBusiness.delete(toDeleteData);
        return retVal;
    }

    @Action(ownermodel = "系统管理-文件分组管理", detail = "删除文件分组信息")
    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(String formData) {
        List<FileGroupWrapper> toUpdateData = JsonUtil.getJavaCollection(new FileGroupWrapper(), formData);
        CommonResponseData retVal = fileGroupBusiness.update(toUpdateData);
        return retVal;
    }

}
