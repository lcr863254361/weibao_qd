package com.orient.template.business.core.support;

import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.template.model.CollabTemplateImportExtraInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * seal some useful function to be used in template,
 *
 * @author Seraph
 *         2016-09-14 下午3:26
 */
public class TemplateNodeImportHelper{

    public TemplateNodeImportHelper(CollabTemplateImportExtraInfo extraInfo){
        this.templateImportExtraInfo = extraInfo;
    }

    public CollabTemplateNode getRootNode(CollabTemplateNode node){

        CollabTemplateNode root = node;
        while(node.getParent() != null){
            root = node.getParent();
        }
        return root;
    }

    /**
     * return all the rootNode's children's old id to new id map;
     * key is node's type + node's old id
     * @param rootNode
     * @param refresh true to refresh the id map's data
     * @return
     */
    public  Map<String, String> getOldNewIdMap(CollabTemplateNode rootNode, boolean refresh){
        Map<String, String> oldNewIdMap = templateNodeToOldNewIdMap.get(rootNode);
        if(oldNewIdMap == null || refresh){
            oldNewIdMap = new HashMap<>();
            recursiveConstructIdMap(rootNode, oldNewIdMap);
            templateNodeToOldNewIdMap.put(rootNode, oldNewIdMap);
        }
        return oldNewIdMap;
    }

    public CollabTemplateImportExtraInfo getTemplateImportExtraInfo() {
        return templateImportExtraInfo;
    }

    private void recursiveConstructIdMap(CollabTemplateNode tempNode, Map<String, String> oldNewIdMap){

        oldNewIdMap.put(tempNode.getType() + tempNode.getOldDataId(), tempNode.getNewDataId());
        List<CollabTemplateNode> children = tempNode.getChildren();

        if(children != null){
            for(CollabTemplateNode templateNode: children){
                recursiveConstructIdMap(templateNode, oldNewIdMap);
            }
        }
    }

    private final CollabTemplateImportExtraInfo templateImportExtraInfo;

    private final Map<CollabTemplateNode, Map<String, String>> templateNodeToOldNewIdMap = new HashMap<>();

}
