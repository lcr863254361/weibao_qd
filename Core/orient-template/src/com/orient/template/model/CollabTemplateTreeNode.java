package com.orient.template.model;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-09-26 下午3:42
 */
public class CollabTemplateTreeNode {

    private String id;
    private String type;
    private String text;
    private boolean bmTemplate;
    private String modelName;
    private String schemaId;
    private boolean leaf;
    private String iconCls;

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isBmTemplate() {
        return bmTemplate;
    }

    public void setBmTemplate(boolean bmTemplate) {
        this.bmTemplate = bmTemplate;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(String schemaId) {
        this.schemaId = schemaId;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
}
