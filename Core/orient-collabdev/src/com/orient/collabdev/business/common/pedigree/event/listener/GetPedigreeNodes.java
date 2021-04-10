package com.orient.collabdev.business.common.pedigree.event.listener;

import com.orient.collabdev.business.common.pedigree.event.GetPedigreeEvent;
import com.orient.collabdev.business.common.pedigree.event.GetPedigreeEventParam;
import com.orient.collabdev.business.structure.StructureBusiness;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.collabdev.model.PedigreeNode;
import com.orient.utils.BeanUtils;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 获取谱系展现所需节点信息
 *
 * @author panduanduan
 * @create 2018-08-04 2:37 PM
 */
@Component
public class GetPedigreeNodes extends OrientEventListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == GetPedigreeEvent.class || GetPedigreeEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        OrientEvent orientEvent = (OrientEvent) applicationEvent;
        GetPedigreeEventParam eventParam = (GetPedigreeEventParam) orientEvent.getParams();
        String queryType = eventParam.getQueryType();
        String pId = eventParam.getNodeId();
        Integer pVersion = eventParam.getNodeVersion();
        if ("brother".equalsIgnoreCase(queryType)) {
            CollabDevNodeDTO pNode = structureBusiness.getParentNode(pId, pVersion);
            if (null != pNode) {
                pId = pNode.getId();
                pVersion = pNode.getVersion();
            }
        }
        List<CollabDevNodeDTO> collabDevNodeDTOS = structureBusiness.getSonNodes(pId, pVersion);
        //only keep task node
        List<CollabDevNodeDTO> taskNodes = collabDevNodeDTOS.stream().filter(collabDevNodeDTO -> CollabDevConstants.NODE_TYPE_TASK
                .equalsIgnoreCase(collabDevNodeDTO.getType())).collect(Collectors.toList());
        //conver to pedigree node
        taskNodes.forEach(collabDevNodeDTO -> {
            PedigreeNode pedigreeNode = new PedigreeNode();
            BeanUtils.copyProperties(pedigreeNode, collabDevNodeDTO);
            eventParam.getPedigreeDTO().getNodes().add(pedigreeNode);
        });
    }

    @Autowired
    StructureBusiness structureBusiness;
}
