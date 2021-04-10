package com.orient.sysman.controller;

import com.orient.log.annotion.Action;
import com.orient.utils.Log.LogThreadLocalHolder;
import com.orient.sysman.bussiness.BackUpJobBusiness;
import com.orient.sysmodel.domain.sys.QuartzJobEntity;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * QuartzJobEntity
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/BackUpJob")
public class BackUpJobController {
    @Autowired
    BackUpJobBusiness backUpJobBusiness;

    @Action(ownermodel = "系统管理-备份恢复", detail = "查看定时备份任务列表")
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<QuartzJobEntity> list(Integer page, Integer limit, QuartzJobEntity filter) {
        return backUpJobBusiness.list(page, limit, filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @Action(ownermodel = "系统管理-备份恢复", detail = "新增定时备份任务-${name}")
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(QuartzJobEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        backUpJobBusiness.save(formValue);
        LogThreadLocalHolder.putParamerter("name",formValue.getBackname());
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @Action(ownermodel = "系统管理-备份恢复", detail = "更新定时备份任务-${name}")
    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(QuartzJobEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        backUpJobBusiness.update(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        LogThreadLocalHolder.putParamerter("name",formValue.getBackname());
        return retVal;
    }

    @Action(ownermodel = "系统管理-备份恢复", detail = "修改定时任务状态")
    @RequestMapping("saveJobStatus")
    @ResponseBody
    public CommonResponseData saveJobStatus(Long[] jobIds, String status) {
        CommonResponseData retVal = new CommonResponseData();
        backUpJobBusiness.saveJobStatus(jobIds,status);
        return retVal;
    }


    /**
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @Action(ownermodel = "系统管理-备份恢复", detail = "删除定时任务")
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(Long[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        backUpJobBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }
}
