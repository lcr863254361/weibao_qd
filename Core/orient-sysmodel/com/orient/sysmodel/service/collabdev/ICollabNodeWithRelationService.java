package com.orient.sysmodel.service.collabdev;

import com.orient.sysmodel.domain.collabdev.CollabNodeWithRelation;
import com.orient.sysmodel.service.IBaseService;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
public interface ICollabNodeWithRelationService extends IBaseService<CollabNodeWithRelation> {

    @Override
    void update(CollabNodeWithRelation collabNodeWithRelation);
}
