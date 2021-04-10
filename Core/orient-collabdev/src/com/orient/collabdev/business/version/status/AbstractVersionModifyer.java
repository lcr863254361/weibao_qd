package com.orient.collabdev.business.version.status;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collabdev.business.common.annotation.MngStatus;
import com.orient.collabdev.business.designing.ResultSettingsBusiness;
import com.orient.collabdev.business.structure.util.ModelDataToNodePO;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.collabdev.constant.ResultSettingBindType;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.utils.CommonTools;
import com.orient.web.util.RequestUtil;
import com.orient.web.util.UserContextUtil;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-07-28 2:16 PM
 */
public abstract class AbstractVersionModifyer implements IVersionModifyer {

    @Autowired
    ICollabNodeService collabNodeService;

    @Autowired
    ResultSettingsBusiness resultSettingsBusiness;

    protected CollabNode createAndBindNode(IBusinessModel bm, Map<String, String> dataMap, String id, Integer nextVersion) {
        CollabNode collabNode = null;
        //创建Node并与之绑定
        HttpServletRequest request = RequestUtil.getHttpServletRequest();
        String parentNodeId = RequestUtil.getString(request, "parentNodeId");
        //if null == parentNodeId then manually constructed parent and child relation later
        ModelDataToNodePO modelDataToNodePO = ModelDataToNodePO.fromString(bm.getName());
        if (null != modelDataToNodePO) {
            collabNode = modelDataToNodePO.converToCollabNode(dataMap, bm);
            //获取排序信息
            List<CollabNode> brotherNodes = collabNodeService.list(Restrictions.eq("pid", parentNodeId));
            int order = CommonTools.isEmptyList(brotherNodes) ? 1 : brotherNodes.stream().max(Comparator.comparingInt(CollabNode::getNodeOrder)).get().getNodeOrder() + 1;
            collabNode.setPid(parentNodeId);
            collabNode.setIsRoot(parentNodeId.startsWith("dir") ? 1 : 0);
            collabNode.setVersion(nextVersion);
            collabNode.setBmDataId(id);
            collabNode.setCreateTime(new Date());
            collabNode.setCreateUser(UserContextUtil.getUserAllName());
            collabNode.setUpdateUser(UserContextUtil.getUserAllName());
            collabNode.setUpdateTime(new Date());
            collabNode.setNodeOrder(order);
            collabNodeService.save(collabNode);
            //节点交付物默认绑定研发数据
            resultSettingsBusiness.modifyNodeBind(collabNode.getId(), ResultSettingBindType.DEV.toString(), true);
        }
        return collabNode;
    }

    protected CollabNode updateNode(IBusinessModel bm, Map<String, String> dataMap, String dataId, String type, Integer nextVersion) {
        CollabNode collabNode = null;
        List<CollabNode> collabNodes = collabNodeService.list(Restrictions.eq("bmDataId", dataId), Restrictions.eq("type", type));
        if (!CommonTools.isEmptyList(collabNodes)) {
            collabNode = collabNodes.get(0);
            ModelDataToNodePO modelDataToNodePO = ModelDataToNodePO.fromString(bm.getName());
            modelDataToNodePO.setCommonAttribute(collabNode, dataMap, bm.getId());
            collabNode.setVersion(nextVersion);
            collabNode.setUpdateTime(new Date());
            collabNode.setUpdateUser(UserContextUtil.getUserAllName());
            collabNodeService.update(collabNode);
        }
        return collabNode;
    }

    protected CollabNode getBindNode(String dataId, String type) {
        List<CollabNode> collabNodes = collabNodeService.list(Restrictions.eq("bmDataId", dataId), Restrictions.eq("type", type));
        if (CommonTools.isEmptyList(collabNodes)) {
            return null;
        } else
            return collabNodes.get(0);
    }

    protected List<CollabNode> getBindNodes(String dataIds, String type) {
        List<CollabNode> collabNodes = collabNodeService.list(Restrictions.in("bmDataId", CommonTools.stringToList(dataIds))
                , Restrictions.eq("type", type));
        if (CommonTools.isEmptyList(collabNodes)) {
            return null;
        } else
            return collabNodes;
    }

    protected ManagerStatusEnum getCurrentStatus() {
        Class operateClass = this.getClass();
        MngStatus classAnnotation = (MngStatus) operateClass.getAnnotation(MngStatus.class);
        return classAnnotation.status();
    }


}
