package com.orient.collabdev.business.structure.util;

import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeWithRelation;
import com.orient.utils.CommonTools;
import com.orient.utils.ReflectUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-02 11:39 AM
 */
public class CollabNodeExtrator {

    public static Map<String, List<String>> extraModelNameAndIds(List<CollabNodeWithRelation> collabNodes) {
        return extraModelNameAndIdsFromCollabNode(collabNodes, "type", "bmDataId");
    }

    public static Map<String, List<String>> extraModelNameAndIdsFromCollabNode(List<CollabNode> collabNodes) {
        return extraModelNameAndIdsFromCollabNode(collabNodes, "type", "bmDataId");
    }

    public static String extraProjectStatus(CollabNodeWithRelation collabNodeWithRelation) {
        if (CollabDevConstants.NODE_TYPE_PRJ.equalsIgnoreCase(collabNodeWithRelation.getType())) {
            return collabNodeWithRelation.getStatus();
        } else
            return extraProjectStatus(collabNodeWithRelation.getParent());
    }


    public static <T> Map<String, List<String>> extraModelNameAndIdsFromCollabNode(List<T> collabNodes, String typeFieldName, String bmDataIdFieldName) {
        Map<String, List<String>> modelNameAndIds = new HashMap<>();
        collabNodes.forEach(collabNode -> {
            String type = CommonTools.Obj2String(ReflectUtil.getFieldValue(collabNode, typeFieldName));
            String bmDataId = CommonTools.Obj2String(ReflectUtil.getFieldValue(collabNode, bmDataIdFieldName));
            String modelName = CollabDevConstants.NODETYPE_MODELNAME_MAPPING.get(type);
            if (!modelNameAndIds.containsKey(modelName)) {
                modelNameAndIds.put(modelName, new ArrayList<>());
            }
            modelNameAndIds.get(modelName).add(bmDataId);
        });
        return modelNameAndIds;
    }
}
