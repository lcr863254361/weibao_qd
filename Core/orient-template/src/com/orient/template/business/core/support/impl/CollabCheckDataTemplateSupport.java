package com.orient.template.business.core.support.impl;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.pvm.bean.CheckModelTreeNode;
import com.orient.pvm.business.CheckModelBusiness;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.pvm.TaskCheckModel;
import com.orient.sysmodel.domain.pvm.TaskCheckRelation;
import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.sysmodel.service.pvm.impl.TaskCheckModelService;
import com.orient.sysmodel.service.pvm.impl.TaskCheckRelationService;
import com.orient.template.business.core.support.TemplateNodeImportHelper;
import com.orient.template.business.core.support.TemplateSupport;
import com.orient.template.model.CollabCheckData;
import com.orient.template.model.CollabTemplatePreviewNode;
import com.orient.utils.CommonTools;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.orient.sysmodel.domain.pvm.TaskCheckModel.TASK_DATA_ID;
import static com.orient.sysmodel.domain.pvm.TaskCheckModel.TASK_MODEL_ID;

/**
 * template support for collab check data
 *
 * @author Seraph
 *         2016-10-18 上午9:37
 */
@Component
public class CollabCheckDataTemplateSupport implements TemplateSupport<CollabCheckData> {

    @Override
    public boolean importNode(CollabTemplateNode<CollabCheckData> currentNode, CollabTemplateNode dependentNode, TemplateNodeImportHelper importHelper) {
        String newTaskId = currentNode.getIndependentCompRef().getNewDataId();
        CollabCheckData collabCheckData = currentNode.getData();
        collabCheckData.getTaskCheckModels().forEach(taskCheckModel -> {
            taskCheckModel.setId(null);
            taskCheckModel.setChecktablestatus(1);
           // taskCheckModel.setTaskdataid(Long.valueOf(newTaskId));
            taskCheckModel.setCheckmodelid(taskCheckModel.getCheckmodelid() == null ? null : taskCheckModel.getCheckmodelid());
            this.taskCheckModelService.save(taskCheckModel);
        });
        if (!CommonTools.isEmptyList(collabCheckData.getTaskCheckRelations())) {
            Map<String, List<String>> modelDataIdList = new HashMap<>();
            //add modelData
            collabCheckData.getTaskCheckDatas().forEach((modelId, dataList) -> {
                dataList.forEach(dataMap -> {
                    //remove id
                    dataMap.remove("ID");
                    IBusinessModel businessModel = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
                    String newId = orientSqlEngine.getBmService().insertModelData(businessModel, dataMap);
                    if (CommonTools.isEmptyList(modelDataIdList.get(modelId))) {
                        modelDataIdList.put(modelId, new ArrayList<String>() {{
                            add(newId);
                        }});
                    } else {
                        modelDataIdList.get(modelId).add(newId);
                    }
                });
            });
            //add taskCheckRelation
            Long taskModelId = collabCheckData.getTaskCheckRelations().get(0).getTaskmodelid();
            modelDataIdList.forEach((modelId, idList) -> {
                idList.forEach(dataId -> {
                    TaskCheckRelation taskCheckRelation = new TaskCheckRelation();
                    taskCheckRelation.setTaskdataid(Long.valueOf(newTaskId));
                    taskCheckRelation.setTaskmodelid(taskModelId);
                    taskCheckRelation.setCheckmodelid(Long.valueOf(modelId));
                    taskCheckRelation.setCheckdataid(Long.valueOf(dataId));
                    taskCheckRelationService.save(taskCheckRelation);
                });
            });
        }
        return false;
    }

    @Override
    public void doExport(CollabTemplateNode<CollabCheckData> templateNode, MutableBoolean dataSetted, List<Serializable> childrenData, List<Serializable> independentComponentsData, List<Serializable> relationComponentsData) {
        CollabCheckData collabCheckData = templateNode.getData();
        List<TaskCheckModel> taskCheckModels = this.taskCheckModelService.list(Restrictions.eq(TASK_MODEL_ID, Long.valueOf(collabCheckData.getTaskModelId())),
                Restrictions.eq(TASK_DATA_ID, Long.valueOf(collabCheckData.getTaskDataId())));
        collabCheckData.getTaskCheckModels().addAll(taskCheckModels);
        List<TaskCheckRelation> taskCheckRelations = this.taskCheckRelationService.list(Restrictions.eq(TASK_MODEL_ID, Long.valueOf(collabCheckData.getTaskModelId())),
                Restrictions.eq(TASK_DATA_ID, Long.valueOf(collabCheckData.getTaskDataId())));
        collabCheckData.getTaskCheckRelations().addAll(taskCheckRelations);
        //save model data
        Map<String, List<String>> modelDataIdList = new HashMap<>();
        taskCheckRelations.forEach(taskCheckRelation -> {
            String modelId = taskCheckRelation.getCheckmodelid().toString();
            String dataId = taskCheckRelation.getCheckdataid().toString();
            if (CommonTools.isEmptyList(modelDataIdList.get(modelId))) {
                modelDataIdList.put(modelId, new ArrayList<String>() {{
                    add(dataId);
                }});
            } else {
                modelDataIdList.get(modelId).add(dataId);
            }
        });
        modelDataIdList.forEach((modelId, idList) -> {
            IBusinessModel businessModel = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
            CustomerFilter customerFilter = new CustomerFilter("ID", EnumInter.SqlOperation.In, CommonTools.list2String(idList));
            businessModel.appendCustomerFilter(customerFilter);
            List<Map<String, String>> dataList = orientSqlEngine.getBmService().createModelQuery(businessModel).list();
            collabCheckData.getTaskCheckDatas().put(modelId, dataList);
        });
    }

    @Override
    public CollabTemplatePreviewNode convertTemplateNodeToPreviewNode(CollabTemplateNode<CollabCheckData> node, String previewType) {
        CollabTemplatePreviewNode collabTemplatePreviewNode = new CollabTemplatePreviewNode(node, previewType);

        node.convertSerialBytesToData();
        CollabCheckData collabCheckData = node.getData();
        List<TaskCheckModel> taskCheckModels = collabCheckData.getTaskCheckModels();


        List<CheckModelTreeNode> checkModelTreeNodes = taskCheckModels.stream()
                .map(taskCheckModel -> this.checkModelBusiness.convertTaskCheckModelToTreeNode(taskCheckModel)).collect(Collectors.toList());

        collabTemplatePreviewNode.addExtraInfo("checkModelTreeNodes", checkModelTreeNodes);

        return collabTemplatePreviewNode;
    }

    @Autowired
    private TaskCheckModelService taskCheckModelService;
    @Autowired
    private TaskCheckRelationService taskCheckRelationService;
    @Autowired
    private CheckModelBusiness checkModelBusiness;
    @Autowired
    protected ISqlEngine orientSqlEngine;
    @Autowired
    protected IBusinessModelService businessModelService;
}
