package com.orient.collabdev.business.common.noderefopera.impl;

import com.orient.collabdev.business.common.annotation.NodeRefOperate;
import com.orient.collabdev.business.common.noderefopera.NodeRefOperateInterface;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.datashare.CollabShareFolder;
import com.orient.sysmodel.service.collabdev.ICollabShareFolderService;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 删除共享数据
 *
 * @author panduanduan
 * @create 2018-08-20 10:40 AM
 */
@NodeRefOperate
public class ShareDataRef extends AbstractNodeRefOperate implements NodeRefOperateInterface {

    @Override
    public boolean deleteNodeRefData(Boolean isUnstarted, CollabNode... nodes) {
        if (isUnstarted) {
            List<String> nodeIds = getNodeIds(nodes);
            List<CollabShareFolder> collabShareFolders = collabShareFolderService.list(Restrictions.in("nodeId", nodeIds));
            collabShareFolders.forEach(collabShareFolder -> collabShareFolderService.delete(collabShareFolder));
        }
        return false;
    }

    @Autowired
    ICollabShareFolderService collabShareFolderService;
}
