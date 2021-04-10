package com.orient.history.core.engine.prepare.impl.audit;

import com.orient.flow.business.FlowTaskBusiness;
import com.orient.history.core.binddata.handler.IBindDataHandler;
import com.orient.history.core.engine.prepare.annotation.PrepareIntermediator;
import com.orient.history.core.engine.prepare.impl.AbstractPrepareIntermediator;
import com.orient.history.core.request.AuditFrontViewRequest;
import com.orient.history.core.request.FrontViewRequest;
import com.orient.history.core.util.HisTaskConstants;
import com.orient.history.core.util.HisTaskThreadLocalHolder;
import com.orient.utils.CommonTools;
import org.jbpm.pvm.internal.history.model.HistoryTaskImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
@PrepareIntermediator(types = {"com.orient.history.core.util.HisTaskTypeConstants$$AUDIT_TASK"}, order = 1)
@Scope(value = "prototype")
public class PreapareCommonAuditIntermediator extends AbstractPrepareIntermediator {

    @Autowired
    FlowTaskBusiness flowTaskBusiness;

    @Override
    public void doPrepare(FrontViewRequest frontViewRequest) {
        super.doPrepare(frontViewRequest);
        //强转为父类
        AuditFrontViewRequest auditFrontViewRequest = (AuditFrontViewRequest) frontViewRequest;
        String taskId = auditFrontViewRequest.getTaskId();
        //获取任务描述
        HistoryTaskImpl task = (HistoryTaskImpl) flowTaskBusiness.getHistoryTaskById(taskId);
        String piId = auditFrontViewRequest.getPiId();
        HisTaskThreadLocalHolder.put(taskId + HisTaskConstants.TASK_DESC_KEY, task);

        //准备freemarker模板
        if (!CommonTools.isEmptyMap(auditFrontViewRequest.getModelFreemarkerHtml())) {
            HisTaskThreadLocalHolder.put(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_FREEMARKER, auditFrontViewRequest.getModelFreemarkerHtml());
        }

        //准备意见
        if (!CommonTools.isEmptyMap(auditFrontViewRequest.getOpinions())) {
            HisTaskThreadLocalHolder.put(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_OPINIONDATA, auditFrontViewRequest.getOpinions());
        }

        //审批任务额外需要保存控制流信息
        HisTaskThreadLocalHolder.put(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_CONTROLFLOWDATA, piId);

    }
}
