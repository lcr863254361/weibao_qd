package com.orient.template.business.core.support;

import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.template.model.CollabTemplatePreviewNode;
import org.apache.commons.lang.mutable.MutableBoolean;

import java.io.Serializable;
import java.util.List;

/**
 * represent that a type of node can be exported/imported by this class
 *
 * @author Seraph
 *         2016-09-13 上午9:50
 */
public interface TemplateSupport<T extends Serializable> {

    /**
     * import template node, for example, perform a save operation
     * before return of this function, the {@code newDataId} of {@link CollabTemplateNode} should be set
     * @param currentNode current node to import
     * @param dependentNode if current node is a <b>direct</b> relation node of another node, this will be the dependent node, otherwise this will be null
     * @param importHelper a helper object that provide some methods that may be used
     * @return true to let the {@link com.orient.template.business.core.TemplateEngine} import
     * its children and independentComponents and relationComponents,
     * false to let the {@link com.orient.template.business.core.TemplateEngine} stop import this node related informations
     */
    boolean importNode(CollabTemplateNode<T> currentNode, CollabTemplateNode dependentNode, TemplateNodeImportHelper importHelper);

    /**
     * convert T to {@link CollabTemplateNode}
     * @param node the node to convert
     * @param dataSetted if set to true, the next three params will be ignore, and relevant datas will be recursively read from return data's relevant get method
     * @param childrenData the children data to continue export
     * @param independentComponentsData the independent components data to continue export
     * @param relationComponentsData the relation components data to continue export
     * @return the converted data
     */
    default CollabTemplateNode<T> exportNode(T node, MutableBoolean dataSetted, List<Serializable> childrenData, List<Serializable> independentComponentsData,
                                   List<Serializable> relationComponentsData){
        CollabTemplateNode templateNode = new CollabTemplateNode();
        templateNode.setData(node);
        templateNode.setType(node.getClass().getName());

        doExport(templateNode, dataSetted, childrenData, independentComponentsData, relationComponentsData);
        return templateNode;
    }

    /**
     * a hook method used by {@code exportNode}
     * @param templateNode
     * @param dataSetted
     * @param childrenData
     * @param independentComponentsData
     * @param relationComponentsData
     */
    default void doExport(CollabTemplateNode<T> templateNode, MutableBoolean dataSetted, List<Serializable> childrenData, List<Serializable> independentComponentsData,
                          List<Serializable> relationComponentsData){

    }

    /**
     * convert a db stored node to a preview node
     * @param node
     * @param previewType
     * @return
     */
    default CollabTemplatePreviewNode convertTemplateNodeToPreviewNode(CollabTemplateNode<T> node, String previewType){
        node.convertSerialBytesToData();

        CollabTemplatePreviewNode collabTemplatePreviewNode = new CollabTemplatePreviewNode(node, previewType);
        List<CollabTemplateNode> independentComponents = node.getIndependentComponents();
        List<CollabTemplateNode> relationComponents = node.getRelationComponents();

        for(CollabTemplateNode comp : independentComponents){
            collabTemplatePreviewNode.addCompInfos(new CollabTemplatePreviewNode.CompInfo(comp.getId(), comp.getType(), false));
        }

        for(CollabTemplateNode comp : relationComponents){
            collabTemplatePreviewNode.addCompInfos(new CollabTemplatePreviewNode.CompInfo(comp.getId(), comp.getType(), true));
        }
        return collabTemplatePreviewNode;
    }

}
