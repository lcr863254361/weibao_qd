package com.orient.collabdev.business.ancestry.taskanalyze.context;

import com.aptx.utils.CollectionUtil;
import com.google.common.collect.Lists;
import com.orient.collabdev.business.ancestry.core.bean.NodeState;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.sysmodel.domain.collabdev.CollabHistoryNode;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeDevStatus;
import com.orient.sysmodel.service.collabdev.impl.CollabNodeDevStatusService;

import java.util.List;

/**
 * Created by karry on 18-9-5.
 */
public class CopyNodeAnalyzeContext extends BaseAnalyzeContext {
    protected CollabNodeDevStatusService collabNodeDevStatusService;

    public CopyNodeAnalyzeContext() {
        super();
        this.collabNodeDevStatusService = applicationContext.getBean(CollabNodeDevStatusService.class);
    }

    public void copyTaskDevStatesInPlan(String pid) {
        //clear original status
        CollabNode planNode = ancestryModelBusiness.getNodeById(pid);
        ancestryModelBusiness.deleteDevStateByPlan(planNode.getId(), Long.valueOf(planNode.getVersion()));
        //copy
        CollabHistoryNode hisPlanNode = ancestryModelBusiness.getMaxVersionedHistoryNode(pid);
        if (hisPlanNode != null) {
            List<CollabNodeDevStatus> oriDevStates = ancestryModelBusiness.getDevStateByPlan(pid, Long.valueOf(hisPlanNode.getVersion()));
            List<CollabNode> tasks = ancestryModelBusiness.getNodesByPid(pid);
            for (CollabNode task : tasks) {
                CollabNodeDevStatus oriDevState = getDevStatusByNodeId(task.getId(), oriDevStates);
                CollabNodeDevStatus devStatus = new CollabNodeDevStatus();
                if (oriDevState != null) {
                    devStatus.setNodeId(task.getId());
                    devStatus.setNodeVersion(Long.valueOf(task.getVersion()));
                    devStatus.setPid(planNode.getId());
                    devStatus.setPversion(Long.valueOf(planNode.getVersion()));
                    devStatus.setTechStatus(oriDevState.getTechStatus());
                    devStatus.setApprovalStatus(oriDevState.getApprovalStatus());
                } else {
                    devStatus.setNodeId(task.getId());
                    devStatus.setNodeVersion(Long.valueOf(task.getVersion()));
                    devStatus.setPid(planNode.getId());
                    devStatus.setPversion(Long.valueOf(planNode.getVersion()));
                    devStatus.setTechStatus(NodeState.TS_OUTDATE);
                    devStatus.setApprovalStatus(NodeState.AS_INVALID);
                }
                collabNodeDevStatusService.save(devStatus);
            }
        }
        else {
            List<CollabNode> tasks = ancestryModelBusiness.getNodesByPid(pid);
            for (CollabNode task : tasks) {
                CollabNodeDevStatus devStatus = new CollabNodeDevStatus();
                devStatus.setNodeId(task.getId());
                devStatus.setNodeVersion(Long.valueOf(task.getVersion()));
                devStatus.setPid(planNode.getId());
                devStatus.setPversion(Long.valueOf(planNode.getVersion()));
                devStatus.setTechStatus(NodeState.TS_OUTDATE);
                devStatus.setApprovalStatus(NodeState.AS_INVALID);
                collabNodeDevStatusService.save(devStatus);
            }
        }

        if(ManagerStatusEnum.UNSTART.toString().equals(planNode.getStatus())) {
            return;
        }
        AdjustNodeAnalyzeContext context = new AdjustNodeAnalyzeContext();
        context.adjustStateAnalyze(pid);
    }

    private CollabNodeDevStatus getDevStatusByNodeId(String id, List<CollabNodeDevStatus> oriDevStates) {
        if (CollectionUtil.isNullOrEmpty(oriDevStates)) {
            return null;
        }
        for (CollabNodeDevStatus oriDevState : oriDevStates) {
            if (oriDevState.getNodeId().equals(id)) {
                return oriDevState;
            }
        }
        return null;
    }
}
