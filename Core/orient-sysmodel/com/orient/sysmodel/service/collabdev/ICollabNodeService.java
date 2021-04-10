package com.orient.sysmodel.service.collabdev;

import com.orient.sysmodel.service.IBaseService;
import com.orient.sysmodel.domain.collabdev.CollabNode;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
public interface ICollabNodeService extends IBaseService<CollabNode> {

    @Override
    void update(CollabNode node);

    CollabNode getPlanNode(CollabNode node);
}
