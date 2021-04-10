package com.orient.sysmodel.service.collab;

import com.orient.sysmodel.domain.collab.CollabFlowRelation;
import com.orient.sysmodel.service.IBaseService;

import java.util.List;

/**
 * ICollabFlowRelationService
 *
 * @author Seraph
 *         2016-08-16 上午10:16
 */
public interface ICollabFlowRelationService extends IBaseService<CollabFlowRelation> {

    List<CollabFlowRelation> getSubCollabFlowDataRelationsCascade(String parentPiId);
}
