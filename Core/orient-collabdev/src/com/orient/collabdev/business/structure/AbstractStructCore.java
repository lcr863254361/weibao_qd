package com.orient.collabdev.business.structure;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collabdev.business.structure.tool.IModelAndNodeHelper;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseBusiness;
import com.orient.web.util.RequestUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-13 4:19 PM
 */
public abstract class AbstractStructCore extends BaseBusiness implements StructCore {

    @Autowired
    IModelAndNodeHelper modelAndNodeHelper;

    @Autowired
    ICollabNodeService collabNodeService;

    @Autowired
    StructureBusiness structureBusiness;


    protected CollabNode getRootNode(IBusinessModel bm, String dataId) {
        CollabNode exampleNode;
        if (StringUtils.isEmpty(dataId)) {
            //create operate
            exampleNode = getFromRequest();
        } else {
            List<CollabNode> collabNodeWithRelationList = modelAndNodeHelper.getOriginNodeByBmData(bm, dataId);
            if (!CommonTools.isEmptyList(collabNodeWithRelationList)) {
                exampleNode = collabNodeWithRelationList.get(0);
            } else
                exampleNode = getFromRequest();
        }
        if (null != exampleNode) {
            CollabDevNodeDTO rootNode = structureBusiness.getRootNode(exampleNode.getId(), null);
            return collabNodeService.getById(rootNode.getId());
        }
        return null;
    }

    private CollabNode getFromRequest() {
        HttpServletRequest request = RequestUtil.getHttpServletRequest();
        String parentNodeId = RequestUtil.getString(request, "parentNodeId");
        if (!StringUtil.isEmpty(parentNodeId)) {
            return collabNodeService.getById(parentNodeId);
        }
        return null;
    }
}
