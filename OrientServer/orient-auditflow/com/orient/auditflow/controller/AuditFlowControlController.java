package com.orient.auditflow.controller;

import com.orient.auditflow.business.AuditFlowControlBusiness;
import com.orient.auditflow.model.AuditFlowStartInfo;
import com.orient.auditflow.model.AuditFlowTaskCommitInfo;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * the audit flow control controller
 *
 * @author Seraph
 *         2016-08-01 上午10:46
 */
@Controller
@RequestMapping("/auditFlow/control")
@Transactional(rollbackFor = Exception.class)
public class AuditFlowControlController {

    /**
     * 启动流程任务,当前仅支持开始节点后只连接一个任务的流程
     *
     * @param startInfo
     * @return
     */
    @RequestMapping("/start")
    @ResponseBody
    public CommonResponseData startAuditFlow(@RequestBody AuditFlowStartInfo startInfo) {
        List<FlowDataRelation> bindDatas = UtilFactory.newArrayList();
        for (AuditFlowStartInfo.BindDataInfo bindData : startInfo.getBindDatas()) {
            bindDatas.add(new FlowDataRelation(bindData.getProcessType(), bindData.getModel(), bindData.getDataId(), JsonUtil.toJson(bindData.getExtraParams())));
        }
        //从流程定义启动 或者 从流程名称启动
        return StringUtil.isEmpty(startInfo.getPdId()) ? auditFlowControlBusiness.startAuditFlow(bindDatas, startInfo.getPdName(),
                startInfo.getFirstTaskUserAssign(), startInfo.getAuditType()) : auditFlowControlBusiness.startAuditFlowByPdId(bindDatas, startInfo.getPdId(),
                startInfo.getTaskUserAssigns(), startInfo.getAuditType());
    }

    @RequestMapping("/commitTask")
    @ResponseBody
    public CommonResponseData commitTask(@RequestBody AuditFlowTaskCommitInfo commitInfo) {

        return auditFlowControlBusiness.commitTask(commitInfo.getFlowTaskId(),
                commitInfo.getTransitionName(), commitInfo.getNextTasksUserAssign(),commitInfo.getOpinions());
    }

    @Autowired
    private AuditFlowControlBusiness auditFlowControlBusiness;
}
