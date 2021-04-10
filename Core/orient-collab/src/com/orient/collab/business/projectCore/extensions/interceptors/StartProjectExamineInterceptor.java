package com.orient.collab.business.projectCore.extensions.interceptors;

import com.orient.auditflow.business.AuditFlowInfoBusiness;
import com.orient.auditflow.config.AuditFlowStatus;
import com.orient.auditflow.config.AuditFlowType;
import com.orient.auditflow.model.AuditFlowInfo;
import com.orient.collab.business.projectCore.constant.ProcessType;
import com.orient.collab.business.projectCore.extensions.mng.CollabProcessInterceptor;
import com.orient.collab.business.projectCore.extensions.mng.CollabProcessMarker;
import com.orient.collab.model.StatefulModel;
import com.orient.edm.init.OrientContextLoaderListener;

import static com.orient.collab.config.CollabConstants.PROJECT;

/**
 * StartProjectExamineInterceptor
 *
 * @author Seraph
 *         2016-08-24 上午9:02
 */
@CollabProcessMarker(order = 1, processType = {ProcessType.START}, models = {PROJECT})
public class StartProjectExamineInterceptor implements CollabProcessInterceptor {

    @Override
    public boolean preHandle(StatefulModel statefulModel, String modelName, ProcessType processType) throws Exception {

        AuditFlowInfoBusiness auditFlowInfoBusiness = OrientContextLoaderListener.Appwac.getBean(AuditFlowInfoBusiness.class);

        AuditFlowInfo wbsBaselineSetAuditInfo = auditFlowInfoBusiness.getAuditFlowInfo(modelName, statefulModel.getId(), AuditFlowType.WbsBaseLineAudit.toString());
        AuditFlowInfo wbsBaselineEditAuditInfo = auditFlowInfoBusiness.getAuditFlowInfo(modelName, statefulModel.getId(), AuditFlowType.WbsBaseLineEditAudit.toString());

        boolean wbsBaselineSetAuditIsLatest = false;
        if (wbsBaselineSetAuditInfo.getStatus() == AuditFlowStatus.NotStarted || wbsBaselineEditAuditInfo.getStatus() == AuditFlowStatus.NotStarted) {
            wbsBaselineSetAuditIsLatest = true;
        } else {
            wbsBaselineSetAuditIsLatest = wbsBaselineSetAuditInfo.getStartTime().after(wbsBaselineEditAuditInfo.getStartTime());
        }

        //add by DuanDuanPan 101定制 去除基线审批状态校验
//        if(wbsBaselineSetAuditIsLatest){
//            switch (wbsBaselineSetAuditInfo.getStatus()){
//                case NotStarted:
//                    throw new CollabFlowControlException("基线尚未审批!");
//                case Active:
//                    throw new CollabFlowControlException("基线审批中!");
//                case EndError:
//                    throw new CollabFlowControlException("基线审批不通过,请修改后再次审批!");
//                default:
//                    break;
//            }
//        }else{
//            switch (wbsBaselineEditAuditInfo.getStatus()){
//                case Active:
//                    throw new CollabFlowControlException("基线修改审批中!");
//                default:
//                    break;
//            }
//        }

        return true;
    }

    @Override
    public void afterCompletion(StatefulModel statefulModel, String modelName, ProcessType processType, Object processResult) throws Exception {

    }
}
