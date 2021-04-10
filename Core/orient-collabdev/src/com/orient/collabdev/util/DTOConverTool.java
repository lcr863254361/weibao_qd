package com.orient.collabdev.util;

import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.sysmodel.domain.collabdev.CollabHistoryNode;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeWithRelation;
import com.orient.utils.BeanUtils;
import com.orient.utils.CommonTools;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-07-27 10:26 AM
 */
public class DTOConverTool {

    public static CollabDevNodeDTO converDirToNodeDTO(Map<String, String> dataMap, String dirModelId) {
        CollabDevNodeDTO retval = new CollabDevNodeDTO();
        retval.setId("dir-" + dataMap.get("ID"));
        retval.setIsRoot(0);
        retval.setName(CommonTools.Obj2String(dataMap.get("NAME_" + dirModelId)));
        retval.setType(CollabDevConstants.NODE_TYPE_DIR);
        retval.setVersion(-1);
        retval.setNodeOrder(Integer.valueOf(dataMap.get("ID")));
        retval.setBmDataId(dataMap.get("ID"));
        return retval;
    }

    public static CollabDevNodeDTO converNativeNodeToDTO(CollabNode node) {
        CollabDevNodeDTO collabDevNodeDTO = new CollabDevNodeDTO();
        if (null != node) {
            BeanUtils.copyProperties(collabDevNodeDTO, node);
        }
        return collabDevNodeDTO;
    }

    public static CollabDevNodeDTO converHistoryNativeNodeToDTO(CollabHistoryNode node) {
        CollabDevNodeDTO collabDevNodeDTO = new CollabDevNodeDTO();
        if (null != node) {
            BeanUtils.copyProperties(collabDevNodeDTO, node);
        }
        return collabDevNodeDTO;
    }

    public static CollabDevNodeDTO converCollabNodePOToDTO(CollabNodeWithRelation node) {
        CollabDevNodeDTO collabDevNodeDTO = new CollabDevNodeDTO();
        if (null != node) {
            BeanUtils.copyProperties(collabDevNodeDTO, node);
        }
        return collabDevNodeDTO;
    }
}
