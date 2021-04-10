package com.orient.sysman.controller;

import com.orient.log.annotion.Action;
import com.orient.sysman.bean.FileGroupItemWrapper;
import com.orient.sysman.bussiness.FileGroupItemBusiness;
import com.orient.sysmodel.domain.file.CwmFileGroupItemEntity;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 文件分组Controller
 *
 * @author enjoy
 * @creare 2016-04-29 8:54
 */
@Controller
@RequestMapping("/fileGroupItem")
public class FileGroupItemController {

    @Autowired
    FileGroupItemBusiness fileGroupItemBusiness;

    @Action(ownermodel = "系统管理-文件分组管理", detail = "查看文件分组项信息")
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<FileGroupItemWrapper> list(Long belongFileGroupId, Integer page, Integer limit, CwmFileGroupItemEntity filter) {
        return fileGroupItemBusiness.list(belongFileGroupId, page, limit, filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @Action(ownermodel = "系统管理-文件分组管理", detail = "新增文件分组项信息")
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(FileGroupItemWrapper formValue) {
        CommonResponseData retVal = new CommonResponseData();
        fileGroupItemBusiness.save(formValue);
        retVal.setMsg("保存成功");
        return retVal;
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    @Action(ownermodel = "系统管理-文件分组管理", detail = "更新文件分组项信息")
    public CommonResponseData update(FileGroupItemWrapper formValue) {
        CommonResponseData retVal = new CommonResponseData();
        fileGroupItemBusiness.update(formValue);
        retVal.setMsg("保存成功");
        return retVal;
    }


    /**
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @Action(ownermodel = "系统管理-文件分组管理", detail = "删除文件分组项信息")
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(Long[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        fileGroupItemBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }
}
