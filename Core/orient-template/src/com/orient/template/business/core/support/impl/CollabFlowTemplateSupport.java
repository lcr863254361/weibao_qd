package com.orient.template.business.core.support.impl;

import com.orient.flow.business.FlowDeployBusiness;
import com.orient.flow.business.FlowDiagramContentBusiness;
import com.orient.flow.business.ProcessDefinitionBusiness;
import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.template.business.core.exception.TemplateOperationException;
import com.orient.template.business.core.support.TemplateNodeImportHelper;
import com.orient.template.business.core.support.TemplateSupport;
import com.orient.template.model.CollabFlow;
import com.orient.template.model.CollabTemplatePreviewNode;
import com.orient.web.util.UserContextUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jbpm.api.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

import static com.orient.collab.config.CollabConstants.COLLAB_PD_NAME_SPERATOR;
import static com.orient.workflow.WorkFlowConstants.JPDLFILE_SUFFIX;

/**
 * support for collab flow's template operation
 *
 * @author Seraph
 *         2016-10-08 下午2:55
 */
@Component
public class CollabFlowTemplateSupport implements TemplateSupport<CollabFlow> {

    @Override
    public boolean importNode(CollabTemplateNode currentNode, CollabTemplateNode dependentNode, TemplateNodeImportHelper importHelper) {
        CollabFlow collabFlow = (CollabFlow) currentNode.getData();
        if(!collabFlow.isHasPd()){
            return false;
        }

        CollabTemplateNode refNode = currentNode.getIndependentCompRef();
        String modelName = collabFlow.getBindModelName();
        String dataId = refNode.getNewDataId();

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(new ByteArrayInputStream(collabFlow.getJpdlContent()));

            String name = modelName + "_" + dataId;
            Element process = document.getRootElement();
            Attribute attribute = process.attribute("name");
            attribute.setValue(name);
            String fileName = name + JPDLFILE_SUFFIX;

            this.flowDeployBusiness.deployJpdl(UserContextUtil.getUserName(), modelName, dataId, fileName,
                    new ByteArrayInputStream(document.asXML().getBytes("UTF-8")));
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new TemplateOperationException("导入流程定义出错,流程定义文件解析出错");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * @param node                      only need the bindModelName and bindDataId fields set
     * @param dataSetted                if set to true, the next three params will be ignore, and relevant datas will be recursively read from return data's relevant get method
     * @param childrenData              the children data to continue export
     * @param independentComponentsData the independent components data to continue export
     * @param relationComponentsData    the relation components data to continue export
     * @return
     */
    @Override
    public CollabTemplateNode exportNode(CollabFlow node, MutableBoolean dataSetted, List<Serializable> childrenData, List<Serializable> independentComponentsData, List<Serializable> relationComponentsData) {
        CollabTemplateNode templateNode = new CollabTemplateNode();
        templateNode.setData(node);
        templateNode.setType(node.getClass().getName());

        String pdKey = node.getBindModelName() + COLLAB_PD_NAME_SPERATOR + node.getBindDataId();
        List<ProcessDefinition> processDefinitionList = this.processDefinitionBusiness.getAllPrcDefsWithPdKeyOrNameDescByVersion(pdKey, true);
        if(processDefinitionList.size() == 0){
            return templateNode;
        }

        node.setHasPd(true);
        ProcessDefinition latestPd = processDefinitionList.get(0);
        InputStream jpdlIs = this.flowDiagramContentBusiness.getFlowJPDLContentAsStream(latestPd.getId());
        try {
            node.setJpdlContent(IOUtils.toByteArray(jpdlIs));
        } catch (IOException e) {
            e.printStackTrace();
            throw new TemplateOperationException("导出流程定义出错");
        }

        return templateNode;
    }

    @Override
    public CollabTemplatePreviewNode convertTemplateNodeToPreviewNode(CollabTemplateNode node, String previewType) {
        CollabTemplatePreviewNode collabTemplatePreviewNode = new CollabTemplatePreviewNode(node, previewType);

        node.convertSerialBytesToData();
        CollabFlow flow = (CollabFlow) node.getData();

        collabTemplatePreviewNode.addExtraInfo("hasPd", false);
        if(flow.isHasPd()){
            collabTemplatePreviewNode.addExtraInfo("hasPd", true);
            collabTemplatePreviewNode.addExtraInfo("jpdl", new String(flow.getJpdlContent()));
        }
        return collabTemplatePreviewNode;
    }

    @Autowired
    private ProcessDefinitionBusiness processDefinitionBusiness;
    @Autowired
    private FlowDiagramContentBusiness flowDiagramContentBusiness;
    @Autowired
    private FlowDeployBusiness flowDeployBusiness;
}
