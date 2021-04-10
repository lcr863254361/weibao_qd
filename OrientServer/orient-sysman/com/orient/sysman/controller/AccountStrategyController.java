package com.orient.sysman.controller;

import com.orient.log.annotion.Action;
import com.orient.sysman.bussiness.AccountStrategyBusiness;
import com.orient.sysmodel.domain.sys.CwmSysAccountStrategyEntity;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 账户策略管理
 *
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/AccountStrategy")
public class AccountStrategyController {
    @Autowired
    AccountStrategyBusiness accountStrategyBusiness;

    @Action(ownermodel = "系统管理-账户策略管理", detail = "查看账户策略信息")
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CwmSysAccountStrategyEntity> list(Integer page, Integer limit, CwmSysAccountStrategyEntity filter) {
        return accountStrategyBusiness.list(page, limit, filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CwmSysAccountStrategyEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        accountStrategyBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CwmSysAccountStrategyEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        accountStrategyBusiness.update(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    /**
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(Long[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        accountStrategyBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    @Action(ownermodel = "系统管理-账户策略管理", detail = "更改账户策略启用状态")
    @RequestMapping("saveStatus")
    @ResponseBody
    public CommonResponseData saveStatus(Long[] strategyIds, String status) {
        CommonResponseData retVal = new CommonResponseData();
        accountStrategyBusiness.saveStatus(strategyIds, status);
        retVal.setMsg("更新成功");
        return retVal;
    }

    @Action(ownermodel = "系统管理-账户策略管理", detail = "更新账户策略值")
    @RequestMapping("saveValue")
    @ResponseBody
    public CommonResponseData saveValue(Long strategyId, String value1, String value2) {
        CommonResponseData retVal = new CommonResponseData();
        accountStrategyBusiness.saveValue(strategyId, value1, value2);
        retVal.setMsg("更新成功");
        return retVal;
    }

    @Action(ownermodel = "系统管理-账户策略管理", detail = "更新账户策略值")
    @RequestMapping("saveTimneStrategyValue")
    @ResponseBody
    public CommonResponseData saveTimneStrategyValue(CwmSysAccountStrategyEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        accountStrategyBusiness.saveValue(formValue.getId(), formValue.getStrategyValue1(), formValue.getStrategyValue2());
        retVal.setMsg("更新成功");
        return retVal;
    }


}
