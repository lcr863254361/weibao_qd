package com.orient.collabdev.business.common.noderefopera.impl;

import com.orient.collabdev.business.common.annotation.NodeRefOperate;
import com.orient.collabdev.business.common.noderefopera.NodeRefOperateInterface;
import com.orient.collabdev.constant.ApprovalTypeEnum;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.approval.CollabSettingsApproval;
import com.orient.sysmodel.service.collabdev.ICollabSettingsApprovalService;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 删除审批设置相关信息
 *
 * @author panduanduan
 * @create 2018-08-20 10:37 AM
 */
@NodeRefOperate
public class AuditSettingsRef extends AbstractNodeRefOperate implements NodeRefOperateInterface {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AuditSettingsRef.class);

    @Override
    public boolean deleteNodeRefData(Boolean isUnstarted, CollabNode... nodes) {
        if (isUnstarted) {
            List<String> nodeIds = getNodeIds(nodes);
            List<CollabSettingsApproval> settingsApprovals = collabSettingsApprovalService.list(Restrictions.in("settingsTarget", nodeIds),
                    Restrictions.eq("approvalType", ApprovalTypeEnum.NODEAPPROVAL.toString()));
            settingsApprovals.forEach(collabSettingsApproval -> collabSettingsApprovalService.delete(collabSettingsApproval));
        }
        return true;
    }

    @Autowired
    ICollabSettingsApprovalService collabSettingsApprovalService;
}
