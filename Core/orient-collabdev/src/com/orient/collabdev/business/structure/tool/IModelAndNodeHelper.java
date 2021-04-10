package com.orient.collabdev.business.structure.tool;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeWithRelation;
import com.orient.utils.Pair;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-02 11:49 AM
 */
public interface IModelAndNodeHelper {

    List<CollabNodeWithRelation> getNodeByBmData(IBusinessModel bm, String dataIds);

    List<CollabNodeWithRelation> getNodeByBmData(IBusinessModel bm, List<String> dataIds);

    List<CollabNode> getOriginNodeByBmData(IBusinessModel bm, String dataIds);

    List<CollabNode> getOriginNodeByBmData(IBusinessModel bm, List<String> dataIds);

    List<Pair<IBusinessModel, List<Map<String, String>>>> getParentModelAndIds(String nodeId);
}
