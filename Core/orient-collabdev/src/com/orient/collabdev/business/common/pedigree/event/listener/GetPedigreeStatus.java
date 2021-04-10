package com.orient.collabdev.business.common.pedigree.event.listener;

import com.orient.collabdev.business.common.pedigree.event.GetPedigreeEvent;
import com.orient.collabdev.business.common.pedigree.event.GetPedigreeEventParam;
import com.orient.collabdev.model.PedigreeNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeDevStatus;
import com.orient.sysmodel.service.collabdev.ICollabNodeDevStatusService;
import com.orient.utils.CommonTools;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 获取研发状态及审批状态信息
 *
 * @author panduanduan
 * @create 2018-08-04 2:39 PM
 */
@Component
public class GetPedigreeStatus extends OrientEventListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == GetPedigreeEvent.class || GetPedigreeEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        //根据计划节点id和计划节点版本获取数据包的技术状态和审批状态
        OrientEvent orientEvent = (OrientEvent) applicationEvent;
        GetPedigreeEventParam eventParam = (GetPedigreeEventParam) orientEvent.getParams();
        String planNodeId = eventParam.getNodeId();
        Integer planNodeVersion = eventParam.getNodeVersion();
        List<PedigreeNode> nodes = eventParam.getPedigreeDTO().getNodes();
        nodes.forEach(node -> {
            List<CollabNodeDevStatus> collabNodeDevStatusList = collabNodeDevStatusService.list(Restrictions.eq("nodeId", node.getId()), Restrictions.eq("pid", planNodeId), Restrictions.eq("pversion", Long.valueOf((planNodeVersion))));
            if (!CommonTools.isEmptyList(collabNodeDevStatusList)) {
                CollabNodeDevStatus collabNodeDevStatus = collabNodeDevStatusList.get(0);
                node.setTechStatus(collabNodeDevStatus.getTechStatus());
                node.setApprovalStatus(collabNodeDevStatus.getTechStatus());
            }
        });
    }

    @Autowired
    ICollabNodeDevStatusService collabNodeDevStatusService;
}
