package com.orient.auditflow.controller;

import com.orient.auditflow.business.AuditFlowInfoBusiness;
import com.orient.auditflow.model.AuditFlowInfo;
import com.orient.auditflow.model.AuditFlowTask;
import com.orient.auditflow.model.TaskAssignerNode;
import com.orient.background.bean.AuditFlowTaskSettingEntityWrapper;
import com.orient.background.business.AuditFlowModelBindBusiness;
import com.orient.background.business.AuditFlowTaskSettingBusiness;
import com.orient.sysmodel.domain.flow.AuditFlowModelBindEntity;
import com.orient.sysmodel.domain.flow.AuditFlowTaskSettingEntity;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.util.UserContextUtil;
import com.orient.workflow.WorkFlowConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * the audit flow information controller
 *
 * @author Seraph
 *         2016-08-01 下午3:09
 */
@Controller
@RequestMapping("/auditFlow/info")
public class AuditFlowInfoController {

    @RequestMapping("/detail")
    @ResponseBody
    public AuditFlowInfo getAuditFlowInfo(String modelName, String dataId, String mainType) {
        return this.auditFlowInfoBusiness.getAuditFlowInfo(modelName, dataId, mainType);
    }

    @RequestMapping("/tasks/my")
    @ResponseBody
    public ExtGridData<AuditFlowTask> getMyAuditFlowTasks(int page, int limit, @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                          @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, String taskName) {
        return this.auditFlowInfoBusiness.getAuditFlowTasks(UserContextUtil.getUserName(), page, limit, startDate, endDate, taskName);
    }


    @RequestMapping("/getAllTaskAssigner")
    @ResponseBody
    public AjaxResponseData<List<TaskAssignerNode>> getAllTaskAssigner(Long bindId, String node) {
        List<TaskAssignerNode> results = new ArrayList<>();
        if ("root".equals(node)) {
            //获取流程定义ID
            AuditFlowModelBindEntity auditFlowModelBindEntity = auditFlowModelBindBusiness.getBaseService().getById(bindId);
            String pdId = auditFlowModelBindEntity.getFlowName() + WorkFlowConstants.PROCESS_NAME_VERSION_CONNECTER + auditFlowModelBindEntity.getFlowVersion();
            //获取任务设置信息
            AuditFlowTaskSettingEntity example = new AuditFlowTaskSettingEntity();
            example.setBelongAuditBind(bindId);
            List<AuditFlowTaskSettingEntityWrapper> auditFlowTaskSettingEntities = auditFlowTaskSettingBusiness.listSpecial(null, null, example).getResults();
            //拼接返回值
            results = auditFlowInfoBusiness.getAllTaskAssigner(pdId, auditFlowTaskSettingEntities);
        }
        return new AjaxResponseData(results);
    }

    @RequestMapping("/getTaskSetting")
    @ResponseBody
    public AjaxResponseData<AuditFlowTaskSettingEntity> getTaskSetting(Long bindId, String taskName) {
        AjaxResponseData<AuditFlowTaskSettingEntity> responseData = new AjaxResponseData<>();
        AuditFlowTaskSettingEntity example = new AuditFlowTaskSettingEntity();
        example.setBelongAuditBind(bindId);
        example.setTaskName(taskName);
        List<AuditFlowTaskSettingEntityWrapper> auditFlowTaskSettingEntities = auditFlowTaskSettingBusiness.listSpecial(null, null, example).getResults();
        if (!CommonTools.isEmptyList(auditFlowTaskSettingEntities)) {
            responseData.setResults(auditFlowTaskSettingEntities.get(0));
        } else {
//            throw new OrientBaseAjaxException("", "未找到【" + taskName + "】相关设置信息");
        }
        return responseData;
    }

    @RequestMapping("/getPiIdByBindModel")
    @ResponseBody
    public AjaxResponseData<String> getPiIdByBindModel(String modelId, String dataId) {
        String piId = auditFlowInfoBusiness.getPiIdByBindModel(modelId, dataId);
        AjaxResponseData responseData = new AjaxResponseData<>(piId);
        if (StringUtil.isEmpty(piId)) {
            responseData.setMsg("还未发起审批流程");
        }
        return responseData;
    }

    @Autowired
    private AuditFlowInfoBusiness auditFlowInfoBusiness;

    @Autowired
    private AuditFlowTaskSettingBusiness auditFlowTaskSettingBusiness;

    @Autowired
    private AuditFlowModelBindBusiness auditFlowModelBindBusiness;
}
