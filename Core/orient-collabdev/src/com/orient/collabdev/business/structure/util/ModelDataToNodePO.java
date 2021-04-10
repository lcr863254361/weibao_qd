package com.orient.collabdev.business.structure.util;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.sysmodel.domain.collabdev.CollabNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/27 0027.
 */
public enum ModelDataToNodePO implements NodePOConver {
    PROJECT(CollabDevConstants.PROJECT) {
        @Override
        public CollabNode converToCollabNode(Map<String, String> dataMap, IBusinessModel businessModel) {
            String modelId = businessModel.getId();
            CollabNode collabNode = new CollabNode();
            collabNode.setType(CollabDevConstants.NODE_TYPE_PRJ);
            setCommonAttribute(collabNode, dataMap, modelId);
            return collabNode;
        }
    }, PLAN(CollabDevConstants.PLAN) {
        @Override
        public CollabNode converToCollabNode(Map<String, String> dataMap, IBusinessModel businessModel) {
            String modelId = businessModel.getId();
            CollabNode collabNode = new CollabNode();
            collabNode.setType(CollabDevConstants.NODE_TYPE_PLAN);
            setCommonAttribute(collabNode, dataMap, modelId);
            return collabNode;
        }
    }, TASK(CollabDevConstants.TASK) {
        @Override
        public CollabNode converToCollabNode(Map<String, String> dataMap, IBusinessModel businessModel) {
            String modelId = businessModel.getId();
            CollabNode collabNode = new CollabNode();
            collabNode.setType(CollabDevConstants.NODE_TYPE_TASK);
            setCommonAttribute(collabNode, dataMap, modelId);
            return collabNode;
        }
    };

    private String name;

    public void setCommonAttribute(CollabNode collabNode, Map<String, String> dataMap, String modelId) {
        collabNode.setName(dataMap.get("NAME_" + modelId));
        collabNode.setStatus(dataMap.get("STATUS_" + modelId));
    }

    ModelDataToNodePO(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private static final Map<String, ModelDataToNodePO> stringToEnum = new HashMap<>();

    static {
        for (ModelDataToNodePO s : values()) {
            stringToEnum.put(s.toString(), s);
        }
    }

    public static ModelDataToNodePO fromString(String name) {
        return stringToEnum.get(name);
    }

}
