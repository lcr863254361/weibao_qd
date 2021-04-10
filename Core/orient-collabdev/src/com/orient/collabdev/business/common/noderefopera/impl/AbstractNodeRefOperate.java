package com.orient.collabdev.business.common.noderefopera.impl;

import com.orient.collabdev.business.common.noderefopera.NodeRefOperateInterface;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.utils.exception.OrientBaseAjaxException;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-20 1:41 PM
 */
public abstract class AbstractNodeRefOperate implements NodeRefOperateInterface {

    protected List<String> getNodeIds(CollabNode... nodes) {
        List<String> nodeIds = new ArrayList<>();
        if (null != nodes && nodes.length > 0) {
            for (CollabNode node : nodes) {
                if (null != node && !StringUtil.isEmpty(node.getId())) {
                    nodeIds.add(node.getId());
                }
            }
        }
        if (CommonTools.isEmptyList(nodeIds)) {
            throw new OrientBaseAjaxException("", "未找到相关节点");
        }
        return nodeIds;
    }
}
