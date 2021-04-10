package com.orient.auditflow.business;

import com.orient.auditflow.model.AuditFlowStartInfo;
import com.orient.web.base.BaseHibernateBusiness;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 审批流程表单相关处理
 */
@Component
public class AuditFlowFormBusiness extends BaseHibernateBusiness {


    /**
     * TODO 暂时忽略校验 后续根据项目情况再行完善
     *
     * @param modelId 模型ID
     * @param dataIds 模型数据集合
     * @param formId  模型展现绑定表单模板ID
     * @return
     */
    public List<AuditFlowStartInfo.BindDataInfo> prepareModelDataAuditFlowData(String modelId, String[] dataIds, String formId) {
        List<AuditFlowStartInfo.BindDataInfo> retVal = new ArrayList<>();
        for (String dataId : dataIds) {
            AuditFlowStartInfo.BindDataInfo bindDataInfo = new AuditFlowStartInfo.BindDataInfo();
            bindDataInfo.setDataId(dataId);
            bindDataInfo.setModel(modelId);
            retVal.add(bindDataInfo);
        }
        return retVal;
    }
}
