package com.orient.collabdev.business.common.noderefopera.impl;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collabdev.business.common.annotation.NodeRefOperate;
import com.orient.collabdev.business.common.noderefopera.NodeRefOperateInterface;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.data.CollabResultBind;
import com.orient.sysmodel.domain.component.CwmComponentModelEntity;
import com.orient.sysmodel.domain.pvm.TaskCheckModel;
import com.orient.sysmodel.domain.pvm.TaskCheckRelation;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.sysmodel.domain.taskdata.DataObjectOldVersionEntity;
import com.orient.sysmodel.service.collabdev.ICollabResultBindSercive;
import com.orient.sysmodel.service.component.IComponentBindService;
import com.orient.sysmodel.service.pvm.ITaskCheckModelService;
import com.orient.sysmodel.service.pvm.ITaskCheckRelationService;
import com.orient.sysmodel.service.taskdata.IDataObjectOldVersionService;
import com.orient.sysmodel.service.taskdata.IDataObjectService;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;

/**
 * 删除交付物相关
 *
 * @author panduanduan
 * @create 2018-08-20 10:37 AM
 */
@NodeRefOperate
public class ResultsSettingsRef extends AbstractNodeRefOperate implements NodeRefOperateInterface {

    @Override
    public boolean deleteNodeRefData(Boolean isUnstarted, CollabNode... nodes) {
        if (isUnstarted) {
            List<String> nodeIds = getNodeIds(nodes);
            nodeIds.forEach(nodeId -> {
                List<CollabResultBind> resultBindList = resultBindSercive.list(Restrictions.eq("nodeId", nodeId));
                if (resultBindList.size() > 0) {
                    CollabResultBind collabResultBind = resultBindList.get(0);
                    if (collabResultBind.getHasDevData() == 1) {
                        //删除研发数据
                        deleteDevData(nodeId);
                    }
                    if (collabResultBind.getHasModelData() == 1) {
                        //删除组件数据，暂时只删除和节点的绑定关系
                        deleteComponentData(nodeId);
                    }
                    if (collabResultBind.getHasPVMData() == 1) {
                        //删除离线数据
                        deletePVMData(nodeId);
                    }
                    //删除交付物绑定表中的绑定记录
                    resultBindSercive.delete(collabResultBind.getId());
                }
            });
        }
        return true;
    }

    private void deleteComponentData(String nodeId) {
        List<CwmComponentModelEntity> componentModelEntityList = componentBindService.list(Restrictions.eq("nodeId", nodeId));
        componentModelEntityList.forEach(cwmComponentModelEntity -> componentBindService.delete(cwmComponentModelEntity.getNodeId()));
    }

    private void deletePVMData(String nodeId) {
        List<TaskCheckModel> taskCheckModelList = taskCheckModelService.list(Restrictions.eq("nodeId", nodeId));
        taskCheckModelList.forEach(taskCheckModel -> taskCheckModelService.delete(taskCheckModel.getId()));
        List<TaskCheckRelation> taskCheckRelationList = taskCheckRelationService.list(Restrictions.eq("nodeId", nodeId));
        taskCheckRelationList.forEach(taskCheckRelation -> {
            Long checkmodelid = taskCheckRelation.getCheckmodelid();
            Long checkdataid = taskCheckRelation.getCheckdataid();
            IBusinessModel bm = businessModelService.getBusinessModelById(checkmodelid.toString(), Table);
            orientSqlEngine.getBmService().delete(bm, checkdataid.toString());
            taskCheckRelationService.delete(taskCheckRelation.getId());
        });
    }

    private void deleteDevData(String nodeId) {
        List<DataObjectEntity> dataObjectEntityList = dataObjectService.list(Restrictions.eq("nodeId", nodeId));
        dataObjectEntityList.forEach(dataObjectEntity -> dataObjectService.delete(dataObjectEntity.getId()));
        List<DataObjectOldVersionEntity> dataObjectOldVersionEntityList = dataObjectOldVersionService.list(Restrictions.eq("nodeId", nodeId));
        dataObjectOldVersionEntityList.forEach(dataObjectOldVersionEntity -> dataObjectService.delete(dataObjectOldVersionEntity.getId()));
    }

    @Autowired
    IDataObjectService dataObjectService;

    @Autowired
    IDataObjectOldVersionService dataObjectOldVersionService;

    @Autowired
    ITaskCheckModelService taskCheckModelService;

    @Autowired
    ITaskCheckRelationService taskCheckRelationService;

    @Autowired
    IComponentBindService componentBindService;

    @Autowired
    ICollabResultBindSercive resultBindSercive;

    @Autowired
    IBusinessModelService businessModelService;

    @Autowired
    ISqlEngine orientSqlEngine;

}
