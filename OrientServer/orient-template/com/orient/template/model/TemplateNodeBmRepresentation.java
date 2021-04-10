package com.orient.template.model;

import java.util.Map;

/**
 * if the template node is exported from bm, then represent it in the bm data type (i.e. map)
 * this will help to preview in bm Form
 *
 * @author Seraph
 *         2016-09-28 下午4:51
 */
public class TemplateNodeBmRepresentation {

    private String schemaId;
    private String modelId;
    private String modelName;

    private Map data;

    public String getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(String schemaId) {
        this.schemaId = schemaId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }
}
