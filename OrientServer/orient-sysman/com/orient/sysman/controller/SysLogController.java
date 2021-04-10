package com.orient.sysman.controller;

import com.orient.log.annotion.Action;
import com.orient.sysman.bean.SysLogWrapper;
import com.orient.sysman.bussiness.SysLogBusiness;
import com.orient.sysmodel.domain.sys.QuartzJobEntity;
import com.orient.sysmodel.domain.sys.SysLog;
import com.orient.utils.Log.LogThreadLocalHolder;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.springmvcsupport.DateEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 日志管理
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/SysLog")
public class SysLogController {
    @Autowired
    SysLogBusiness sysLogBusiness;

    @Action(ownermodel = "系统管理-日志管理", detail = "查看系统日志信息")
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<SysLog> list(Integer page, Integer limit, SysLogWrapper filter) {
        ExtGridData<SysLog> retVal = sysLogBusiness.list(page, limit, filter);
        return retVal;
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(SysLog formValue) {
        CommonResponseData retVal = new CommonResponseData();
        sysLogBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(SysLog formValue) {
        CommonResponseData retVal = new CommonResponseData();
        sysLogBusiness.update(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    /**
     * 备份日志，下载txt文件到用户本地
     * @param ids
     * @param request
     * @param response
     */
    @RequestMapping("backup")
    @ResponseBody
    public void createBackupTxtFile(Long[] ids,HttpServletRequest request,HttpServletResponse response) {
        sysLogBusiness.createBackUpTxtFile(ids, request, response);
    }

    /**
     * 设置日志备份定时器
     */
    @RequestMapping("timeBackUp")
    @ResponseBody
    public CommonResponseData createTimeBackUp(QuartzJobEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        sysLogBusiness.createBackUpJob(formValue);
        LogThreadLocalHolder.putParamerter("name", formValue.getBackname());
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    /**
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @Action(ownermodel = "系统管理-日志管理", detail = "删除系统日志信息")
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(Long[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        sysLogBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * 时间格式处理
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DateEditor());
    }
}
