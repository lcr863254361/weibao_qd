package com.orient.template.model;

/**
 * store some extra information while importing template
 *
 * @author Seraph
 *         2016-09-24 上午11:22
 */
public class CollabTemplateImportExtraInfo {

    private Object mountNode;

    private String newRootNodeName;

    public Object getMountNode() {
        return mountNode;
    }

    public void setMountNode(Object mountNode) {
        this.mountNode = mountNode;
    }

    public String getNewRootNodeName() {
        return newRootNodeName;
    }

    public void setNewRootNodeName(String newRootNodeName) {
        this.newRootNodeName = newRootNodeName;
    }
}
