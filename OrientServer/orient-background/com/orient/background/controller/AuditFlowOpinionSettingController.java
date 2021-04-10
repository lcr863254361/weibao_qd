package com.orient.background.controller;

import com.orient.background.business.AuditFlowOpinionSettingBusiness;
import com.orient.sysmodel.domain.flow.AuditFlowOpinionSettingEntity;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 审批流程意见设置
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/AuditFlowOpinionSetting")
public class AuditFlowOpinionSettingController  extends BaseController{
    @Autowired
    AuditFlowOpinionSettingBusiness auditFlowOpinionSettingBusiness;

    @RequestMapping("init")
    @ResponseBody
    public ExtGridData<AuditFlowOpinionSettingEntity> init(Integer page, Integer limit, AuditFlowOpinionSettingEntity filter) {
        return auditFlowOpinionSettingBusiness.list(page,limit,filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(AuditFlowOpinionSettingEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        auditFlowOpinionSettingBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(@RequestBody AuditFlowOpinionSettingEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        auditFlowOpinionSettingBusiness.update(formValue);
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
        auditFlowOpinionSettingBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }
}
