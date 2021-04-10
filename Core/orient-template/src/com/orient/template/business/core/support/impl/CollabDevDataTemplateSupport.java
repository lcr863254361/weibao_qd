package com.orient.template.business.core.support.impl;

import com.orient.collab.model.Plan;
import com.orient.collab.model.Project;
import com.orient.collab.model.Task;
import com.orient.devdataobj.bean.DataObjectBean;
import com.orient.devdataobj.business.DataObjectBusiness;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.sysmodel.service.taskdata.IDataObjectService;
import com.orient.template.business.core.support.TemplateNodeImportHelper;
import com.orient.template.business.core.support.TemplateSupport;
import com.orient.template.model.CollabDevData;
import com.orient.template.model.CollabTemplatePreviewNode;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.web.util.UserContextUtil;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * support for collab data dev data's template operation
 *
 * @author Seraph
 *         2016-10-14 上午10:56
 */
@Component
public class CollabDevDataTemplateSupport  implements TemplateSupport<CollabDevData> {
    @Override
    public boolean importNode(CollabTemplateNode<CollabDevData> currentNode, CollabTemplateNode dependentNode, TemplateNodeImportHelper importHelper) {
        CollabDevData devData = currentNode.getData();
        Date currentDate = new Date();
        String currentUserId = UserContextUtil.getUserId();
        String newRefDataId = currentNode.getIndependentCompRef().getNewDataId();

        Consumer<DataObjectEntity> dataObjectEntityConsumer = dataObjectEntity -> {
            dataObjectEntity.setId(null);
            dataObjectEntity.setCreatetime(currentDate);
            dataObjectEntity.setCreaterid(currentUserId);
            dataObjectEntity.setModifierid(null);
            dataObjectEntity.setModifytime(null);
            dataObjectEntity.setVersion("1.0.0.0");
            //dataObjectEntity.setDataid(newRefDataId);
        };

        Map<Integer, Integer> complicatedDataIdMap = UtilFactory.newHashMap();
        Optional.ofNullable(devData.getBindDatas()).ifPresent(dataObjectEntities -> {
            dataObjectEntities.stream().filter(dataObjectEntity -> dataObjectEntity.getParentdataobjectid() == 0).forEach(dataObjectEntity -> {
                Integer oldEntityId = dataObjectEntity.getId();
                dataObjectEntityConsumer.accept(dataObjectEntity);
                this.dataObjectService.save(dataObjectEntity);
                if(dataObjectEntity.getIsref() == 8){
                    complicatedDataIdMap.put(oldEntityId, dataObjectEntity.getId());
                }
            });
        });

        Optional.ofNullable(devData.getBindDatas()).ifPresent(dataObjectEntities -> {
            dataObjectEntities.stream().filter(dataObjectEntity -> dataObjectEntity.getParentdataobjectid() != 0)
                    .forEach(dataObjectEntity -> {
                        dataObjectEntityConsumer.accept(dataObjectEntity);
                        Integer newParentDataObjectId = complicatedDataIdMap.get(dataObjectEntity.getParentdataobjectid());
                        if(newParentDataObjectId != null){
                            dataObjectEntity.setParentdataobjectid(newParentDataObjectId);
                            this.dataObjectService.save(dataObjectEntity);
                        }
            });
        });

        return false;
    }

    @Override
    public void doExport(CollabTemplateNode<CollabDevData> templateNode, MutableBoolean dataSetted, List<Serializable> childrenData, List<Serializable> independentComponentsData, List<Serializable> relationComponentsData) {
        CollabDevData devData = templateNode.getData();
        List<DataObjectEntity> dataObjectEntities = this.dataObjectService.list(Restrictions.eq("modelid", devData.getModelId()),
                Restrictions.eq("dataid", devData.getDataId()));
        devData.setBindDatas(dataObjectEntities);
    }

    @Override
    public CollabTemplatePreviewNode convertTemplateNodeToPreviewNode(CollabTemplateNode<CollabDevData> node, String previewType) {
        CollabTemplatePreviewNode collabTemplatePreviewNode = new CollabTemplatePreviewNode(node, previewType);
        node.convertSerialBytesToData();

        CollabDevData devData = node.getData();

        List<DataObjectEntity> bindDatas = devData.getBindDatas() == null? UtilFactory.newArrayList() : devData.getBindDatas();
        bindDatas.addAll(getParentBindDataObj(node.getIndependentCompRef().getParent()));

        List<DataObjectBean> oriPreviewDatas = this.dataObjectBusiness.dataChange(bindDatas);
        List<DataObjectBean> previewDatasWithSubParentRelation = UtilFactory.newArrayList();
        oriPreviewDatas.forEach(oriPreviewData -> {
            if(oriPreviewData.getParentdataobjectid() == 0){
                previewDatasWithSubParentRelation.add(oriPreviewData);
                oriPreviewData.setChildren(UtilFactory.newArrayList());
            }
        });

        oriPreviewDatas.forEach(oriPreviewData -> {
            if(oriPreviewData.getParentdataobjectid() != 0){
                previewDatasWithSubParentRelation.forEach(
                    previewDataWithSubParentRelation ->{
                        if(oriPreviewData.getParentdataobjectid().equals(previewDataWithSubParentRelation.getId())){
                            previewDataWithSubParentRelation.getChildren().add(oriPreviewData);
                        }
                    }
                );
            }
        });

        List<DataObjectBean> privatePreviewDatas = previewDatasWithSubParentRelation.stream().filter(oriPreviewData -> oriPreviewData.getIsglobal() == 0).collect(Collectors.toList());
        List<DataObjectBean> publicPreviewDatas = previewDatasWithSubParentRelation.stream().filter(oriPreviewData -> oriPreviewData.getIsglobal() == 1).collect(Collectors.toList());;
        collabTemplatePreviewNode.addExtraInfo("publicDataInstances", publicPreviewDatas);
        collabTemplatePreviewNode.addExtraInfo("privateDataInstances", privatePreviewDatas);

        return collabTemplatePreviewNode;
    }

    private List<DataObjectEntity> getParentBindDataObj(CollabTemplateNode refNodeParent){
        if(refNodeParent.getType().equals(Project.class.getName())){
            return UtilFactory.newArrayList();
        }

        List<CollabTemplateNode> indepentCmps = refNodeParent.getIndependentComponents();
        CollabTemplateNode parentCollabDevDataTemplateNode = indepentCmps.stream()
                .filter(indepentCmp->CollabDevData.class.getName().equals(indepentCmp.getType())).collect(Collectors.toList()).get(0);

        parentCollabDevDataTemplateNode.convertSerialBytesToData();
        if(parentCollabDevDataTemplateNode.getData() == null){
            return UtilFactory.newArrayList();
        }
        return ((CollabDevData) parentCollabDevDataTemplateNode.getData()).getBindDatas().stream()
                .filter(dataObjectEntity -> dataObjectEntity.getIsglobal() == 1).collect(Collectors.toList());
    }

    @Autowired
    private DataObjectBusiness dataObjectBusiness;
    @Autowired
    private IDataObjectService dataObjectService;
}
