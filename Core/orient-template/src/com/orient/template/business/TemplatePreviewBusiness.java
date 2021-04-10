package com.orient.template.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.annotation.BindModel;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.metamodel.metadomain.Schema;
import com.orient.sqlengine.util.BusinessDataConverter;
import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.sysmodel.service.template.impl.CollabTemplateNodeService;
import com.orient.template.business.core.TemplateEngine;
import com.orient.template.model.CollabTemplatePreviewNode;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * TemplatePreviewBusiness
 *
 * @author Seraph
 *         2016-09-21 下午4:40
 */
@Component
public class TemplatePreviewBusiness extends BaseBusiness {

    /**
     * to preview a component, we should prepare the data as the component original needs
     * @param templateId
     * @param nodeId
     * @param previewType
     * @return
     */
    public CollabTemplatePreviewNode getComp(String templateId, String nodeId, String previewType) {
        CollabTemplateNode node = this.collabTemplateNodeService.getById(Long.valueOf(nodeId));

        return this.templateEngine.convertTemplateNodeToPreviewNode(node, previewType);
    }

    public List<CollabTemplatePreviewNode> getNextLayerNodes(String templateId, String parentId) {
        List<CollabTemplatePreviewNode> retPreviewNodes = UtilFactory.newArrayList();

        if("-1".equals(parentId)){
            CollabTemplateNode root = this.collabTemplateNodeService.get(Restrictions.eq(CollabTemplateNode.ROOT, true), Restrictions.eq(CollabTemplateNode.TEMPLATE_ID, templateId));
            retPreviewNodes.add(this.templateEngine.convertTemplateNodeToPreviewNode(root, CollabTemplatePreviewNode.PREVIEW_TYPE_TREE));
            return retPreviewNodes;
        }

        CollabTemplateNode parentNode = this.collabTemplateNodeService.getById(Long.valueOf(parentId));
        List<CollabTemplateNode> collabTemplateNodes = parentNode.getChildren();

        for(CollabTemplateNode collabTemplateNode : collabTemplateNodes){
            retPreviewNodes.add(this.templateEngine.convertTemplateNodeToPreviewNode(collabTemplateNode, CollabTemplatePreviewNode.PREVIEW_TYPE_TREE));
        }

        return retPreviewNodes;
    }

    public Map covertBmTemplateNodeToBmData(String nodeId) {
        CollabTemplateNode node = this.collabTemplateNodeService.getById(Long.valueOf(nodeId));
        node.convertSerialBytesToData();
        Object data = node.getData();

        BindModel bindModel = data.getClass().getAnnotation(BindModel.class);
        String bindBmName = bindModel.modelName();
        String bindSchemaName = bindModel.schemaName();
        Schema schema = this.metaEngine.getMeta(false).getLastVersionOfName(bindSchemaName);
        String schemaId = schema.getId();

        IBusinessModel bm = this.businessModelService.getBusinessModelBySName(bindBmName, schemaId, EnumInter.BusinessModelEnum.Table);
        Map<String, String> retV = BusinessDataConverter.convertBeanToRealColMap(bm, data, true);

        List dataList = UtilFactory.newArrayList();
        dataList.add(retV);
        businessModelService.dataChangeModel(orientSqlEngine, bm, dataList, false);
        retV.put("schemaId", schemaId);
        retV.put("modelId", bm.getId());
        retV.put("modelName", bindBmName);

        return retV;
    }

    @Autowired
    private CollabTemplateNodeService collabTemplateNodeService;
    @Autowired
    private TemplateEngine templateEngine;
}
