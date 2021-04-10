package com.orient.auditflow.controller;

import com.orient.auditflow.business.AuditFlowFormBusiness;
import com.orient.background.business.AuditFlowTaskSettingBusiness;
import com.orient.sysmodel.domain.flow.AuditFlowTaskSettingEntity;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * the audit flow form controller
 *
 * @author Seraph
 *         2016-08-01 上午10:57
 */
@Controller
@RequestMapping("/auditFlow/auditForm")
public class AuditFlowFormController extends BaseController {

    @RequestMapping("prepareAuditFlowData")
    @ResponseBody
    public AjaxResponseData<AuditFlowTaskSettingEntity> prepareModelDataAuditFlowData(Long auditFlowBindId, String taskName) {
        AuditFlowTaskSettingEntity auditFlowTaskSettingEntity = auditFlowTaskSettingBusiness.getByBindIdAndTaskName(auditFlowBindId,taskName);
        AjaxResponseData<AuditFlowTaskSettingEntity> responseData = new AjaxResponseData<>(auditFlowTaskSettingEntity);
        return responseData;
    }

    @Autowired
    AuditFlowFormBusiness auditFlowFormBusiness;

    @Autowired
    AuditFlowTaskSettingBusiness auditFlowTaskSettingBusiness;
}
