package com.orient.background.controller;

import com.orient.background.bean.AuditFlowTaskSettingEntityWrapper;
import com.orient.background.business.AuditFlowTaskSettingBusiness;
import com.orient.sysmodel.domain.flow.AuditFlowTaskSettingEntity;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 审批流程任务设置
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/AuditFlowTaskSetting")
public class AuditFlowTaskSettingController {
    @Autowired
    AuditFlowTaskSettingBusiness auditFlowTaskSettingBusiness;

    @RequestMapping("init")
    @ResponseBody
    public ExtGridData<AuditFlowTaskSettingEntityWrapper> init(Integer page, Integer limit, AuditFlowTaskSettingEntity filter) {
        return auditFlowTaskSettingBusiness.listSpecial(page,limit,filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(AuditFlowTaskSettingEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        auditFlowTaskSettingBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(@RequestBody AuditFlowTaskSettingEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        auditFlowTaskSettingBusiness.update(formValue);
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
        auditFlowTaskSettingBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }
}
