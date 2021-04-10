package com.orient.collabdev.business.ancestry.taskanalyze;

import com.aptx.utils.CollectionUtil;
import com.orient.collabdev.business.ancestry.taskanalyze.context.*;
import com.orient.collabdev.business.version.ICollabVersionMng;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.devdataobj.util.DataObjVersionUtil;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.sysmodel.service.taskdata.IDataObjectService;
import com.orient.utils.BeanUtils;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.orient.collabdev.constant.CollabDevConstants.NODE_TYPE_PLAN;
import static com.orient.collabdev.constant.CollabDevConstants.NODE_TYPE_PRJ;
import static com.orient.collabdev.constant.CollabDevConstants.NODE_TYPE_TASK;

/**
 * Created by karry on 18-8-30.
 */
@Service
public class AncestryMainBusiness {
    @Autowired
    ICollabVersionMng collabVersionMng;

    @Autowired
    AncestryModelBusiness ancestryModelBusiness;

    @Autowired
    IDataObjectService dataObjectService;

    //提交数据包的版本升级，包括协同节点版本、研发数据DataObject版本
    public boolean saveVersion(String nodeId) {
        CollabNode planNode = ancestryModelBusiness.getParentNodeByType(nodeId, CollabDevConstants.NODE_TYPE_PLAN);
        //计划未启动时，禁止数据包提交
        if (planNode.getStatus().equals(ManagerStatusEnum.UNSTART.toString())) {
            throw new OrientBaseAjaxException("", "计划未启动，数据包禁止提交");
        } else {
            //increase collabNode version
            collabVersionMng.increaseVersion(nodeId);
        }

        return true;
    }

    public void saveStatesAndDepends(String nodeId, Map<String, Integer> dependsMap) {
        CollabNode planNode = ancestryModelBusiness.getParentNodeByType(nodeId, CollabDevConstants.NODE_TYPE_PLAN);
        CopyNodeAnalyzeContext context = new CopyNodeAnalyzeContext();
        context.copyTaskDevStatesInPlan(planNode.getId());

        ancestryModelBusiness.deleteNodeDepends(nodeId, planNode.getId(), planNode.getVersion());
        ancestryModelBusiness.saveNodeDepends(nodeId, dependsMap);
    }

    public void dataObjectRollback(String nodeId, Integer toVersion) {
        CollabNode node = ancestryModelBusiness.getNodeById(nodeId);
        List<DataObjectEntity> currentDataObjs = dataObjectService.list(Restrictions.eq("nodeId", nodeId), Restrictions.eq("nodeVersion", node.getVersion()));
        if (!CollectionUtil.isNullOrEmpty(currentDataObjs)) {
            for(DataObjectEntity currentDataObj : currentDataObjs) {
                dataObjectService.delete(currentDataObj);
            }
        }
        List<DataObjectEntity> oriDataObjs = dataObjectService.list(Restrictions.eq("nodeId", nodeId), Restrictions.eq("nodeVersion", toVersion));
        if (!CollectionUtil.isNullOrEmpty(oriDataObjs)) {
            for(DataObjectEntity oriDataObj : oriDataObjs) {
                DataObjectEntity parentDataObj = new DataObjectEntity();   //复制一级研发数据
                BeanUtils.copyProperties(parentDataObj, oriDataObj);
                parentDataObj.setId(null);
                parentDataObj.setNodeVersion(node.getVersion());              //设置新的节点版本号
                resetVersion(parentDataObj, node.getType());
                dataObjectService.save(parentDataObj);
                Integer parentDataObjectEntityId = parentDataObj.getId();
                List<DataObjectEntity> childDataObjs = new ArrayList<>();               //复制二级研发数据
                dataObjectService.getChildDataObjEntity(oriDataObj, childDataObjs);
                if (childDataObjs.size() > 0) {
                    for(DataObjectEntity childDataObj : childDataObjs) {
                        DataObjectEntity dest = new DataObjectEntity();
                        BeanUtils.copyProperties(dest, childDataObj);
                        if (dest.getParentdataobjectid() != 0) {
                            dest.setParentdataobjectid(parentDataObjectEntityId);
                        }
                        dest.setId(null);
                        dest.setNodeVersion(node.getVersion());
                        resetVersion(dest, node.getType());
                        dataObjectService.save(dest);
                    }
                }
            }
        }
    }

    private void resetVersion(DataObjectEntity parentDataObjectEntity, String nodeType) {
        switch (nodeType) {
            case NODE_TYPE_PRJ:
                parentDataObjectEntity.setVersion(DataObjVersionUtil.projectSubmitUpVersion(parentDataObjectEntity.getVersion()));
                break;
            case NODE_TYPE_PLAN:
                parentDataObjectEntity.setVersion(DataObjVersionUtil.jobSubmitUpVersion(parentDataObjectEntity.getVersion()));
                break;
            case NODE_TYPE_TASK:
                parentDataObjectEntity.setVersion(DataObjVersionUtil.dataTaskSubmit(parentDataObjectEntity.getVersion()));
                break;
            default:
                break;
        }
    }

    /***************************************************************************/
    public void taskStateAnalyze(String nodeId, Integer version) {
        BasicNodeAnalyzeContext context = new BasicNodeAnalyzeContext();
        context.taskStateAnalyze(nodeId);
    }

    public void rollbackStateAnalyze(String nodeId, Integer version) {
        RollbackNodeAnalyzeContext context = new RollbackNodeAnalyzeContext();
        context.rollbackStateAnalyze(nodeId);
    }

    public void needlessStateAnalyze(String nodeId, Integer version) {
        BasicNodeAnalyzeContext context = new BasicNodeAnalyzeContext();
        context.taskStateAnalyze(nodeId);
    }
}
