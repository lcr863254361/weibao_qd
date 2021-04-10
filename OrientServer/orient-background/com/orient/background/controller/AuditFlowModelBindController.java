package com.orient.background.controller;

import com.orient.background.business.AuditFlowModelBindBusiness;
import com.orient.flow.util.FlowTypeHelper;
import com.orient.sysmodel.domain.flow.AuditFlowModelBindEntity;
import com.orient.web.base.*;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 审批流程与模型绑定设置
 *
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/AuditFlowModelBind")
public class AuditFlowModelBindController extends BaseController {
    @Autowired
    AuditFlowModelBindBusiness auditFlowModelBindBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<AuditFlowModelBindEntity> list(Integer page, Integer limit, AuditFlowModelBindEntity filter) {
        return auditFlowModelBindBusiness.list(page, limit, filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(AuditFlowModelBindEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        auditFlowModelBindBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("createMulti")
    @ResponseBody
    public CommonResponseData createMulti(@RequestBody List<AuditFlowModelBindEntity> datas) {
        CommonResponseData retVal = new CommonResponseData();
        datas.forEach(auditFlowModelBindEntity -> {
            auditFlowModelBindEntity.setLastUpdateDate(new Date());
            auditFlowModelBindEntity.setUserName(UserContextUtil.getUserAllName());
            auditFlowModelBindBusiness.save(auditFlowModelBindEntity);
        });
        return retVal;
    }


    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(AuditFlowModelBindEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        auditFlowModelBindBusiness.update(formValue);
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
        auditFlowModelBindBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * @param modelId
     * @return 获取未选中的所有审批流程信息
     */
    @RequestMapping("listAuditPds")
    @ResponseBody
    public AjaxResponseData<List<Map<String, String>>> listAuditPds(String modelId) {
        List<String> pdIds = FlowTypeHelper.getMainAuditFlowPdIds();
        List<Map<String, String>> unSelectedAuditFlowInfos = auditFlowModelBindBusiness.listAuditPds(modelId, pdIds);
        return new AjaxResponseData(unSelectedAuditFlowInfos);
    }

    /**
     * @param modelId
     * @return 根据所选模型 获取其绑定的流程定义集合
     */
    @RequestMapping("getModelBindPds")
    @ResponseBody
    public ExtComboboxResponseData<ExtComboboxData> getModelBindPds(String modelId) {
        ExtComboboxResponseData<ExtComboboxData> retValue = new ExtComboboxResponseData<>();
        List<AuditFlowModelBindEntity> bindPds = auditFlowModelBindBusiness.getModelBindPds(modelId);
        bindPds.forEach(auditFlowModelBindEntity -> {
            ExtComboboxData extComboboxData = new ExtComboboxData();
            extComboboxData.setId(auditFlowModelBindEntity.getId().toString());
            extComboboxData.setValue(auditFlowModelBindEntity.getFlowName() + "-" + auditFlowModelBindEntity.getFlowVersion());
            retValue.getResults().add(extComboboxData);
        });
        retValue.setTotalProperty(bindPds.size());
        return retValue;
    }

}
