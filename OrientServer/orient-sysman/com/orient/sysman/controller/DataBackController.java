package com.orient.sysman.controller;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.log.annotion.Action;
import com.orient.sysman.bussiness.DataBackBusiness;
import com.orient.sysman.event.BackUpEvent;
import com.orient.sysman.eventtParam.BackUpEventParam;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.springmvcsupport.DateEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.orient.sysmodel.domain.sys.CwmBackEntity;

import java.util.Date;

/**
 * 系统数据备份恢复
 *
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/DataBack")
public class DataBackController {
    @Autowired
    DataBackBusiness dataBackBusiness;

    @Action(ownermodel = "系统管理-备份恢复", detail = "查看已备份列表")
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CwmBackEntity> list(Integer page, Integer limit, CwmBackEntity filter) {
        return dataBackBusiness.list(page, limit, filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    @Action(ownermodel = "系统管理-备份恢复", detail = "手动备份系统")
    public CommonResponseData create(CwmBackEntity formValue) {
        BackUpEventParam eventParam = new BackUpEventParam(formValue);
        OrientContextLoaderListener.Appwac.publishEvent(new BackUpEvent(this, eventParam));
        dataBackBusiness.save(formValue);
        CommonResponseData retVal = new CommonResponseData();
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @Action(ownermodel = "系统管理-备份恢复", detail = "修改已备份备注")
    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CwmBackEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        dataBackBusiness.update(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    /**
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @Action(ownermodel = "系统管理-备份恢复", detail = "删除已备份信息")
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(Long[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        dataBackBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * 删除表格
     *
     * @param backUpId
     * @return
     */
    @Action(ownermodel = "系统管理-备份恢复", detail = "恢复系统数据")
    @RequestMapping("doRecovery")
    @ResponseBody
    public CommonResponseData doRecovery(Long backUpId) {
        CommonResponseData retVal = new CommonResponseData();
        dataBackBusiness.doRecovery(backUpId);
        retVal.setMsg("请重新启动服务!");
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
