package com.orient.dsrestful.domain;

import java.io.Serializable;

/**
 * Created by GNY on 2018/3/26
 */
public class CommonSchema implements Serializable {

    private String schemaName;
    private String version;

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

}
