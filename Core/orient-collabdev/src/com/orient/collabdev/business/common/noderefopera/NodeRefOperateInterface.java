package com.orient.collabdev.business.common.noderefopera;

import com.orient.sysmodel.domain.collabdev.CollabNode;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-20 10:31 AM
 */
public interface NodeRefOperateInterface {

    /**
     * 删除节点相关信息
     *
     * @param isUnstarted 项目状态是否为未启动状态
     * @param nodes       待删除关联关系的节点id集合
     * @return 是否删除成功
     */
    boolean deleteNodeRefData(Boolean isUnstarted, CollabNode... nodes);

    default void importNodeRefData(CollabNode collabNode) {

    }

    default void exportNodeRefData(CollabNode collabNode) {

    }
}
