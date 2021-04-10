package com.orient.dsrestful.domain.share;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GNY on 2018/3/27
 */
public class ShareSchema implements Serializable {

    private String schemaId;
    private String schemaName;
    private String version;
    private List<ShareTable> tableList;

    public ShareSchema() {
    }

    public ShareSchema(String schemaId, String schemaName, String version) {
        this.schemaId = schemaId;
        this.schemaName = schemaName;
        this.version = version;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<ShareTable> getTableList() {
        return tableList;
    }

    public void setTableList(List<ShareTable> tableList) {
        this.tableList = tableList;
    }

}
