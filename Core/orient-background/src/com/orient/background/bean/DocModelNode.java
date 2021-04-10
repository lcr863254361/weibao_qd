package com.orient.background.bean;

import com.orient.web.model.BaseNode;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-12-06 10:10
 */
public class DocModelNode extends BaseNode {

    private String modelId;

    private String schemaId;

    private String schemaName;

    private String docHandler = "表格处理器";

    public String getDocHandler() {
        return docHandler;
    }

    public void setDocHandler(String docHandler) {
        this.docHandler = docHandler;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(String schemaId) {
        this.schemaId = schemaId;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public DocModelNode(String id, String text, String iconCls, Boolean leaf) {
        super(id, text, iconCls, leaf);
    }
}
