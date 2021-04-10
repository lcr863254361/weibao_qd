package com.orient.dsrestful.domain.schemaBaseInfo;

/**
 * 返回前台的schema数据类
 * Created by GNY on 2018/3/23
 */
public class FrontSchema {

    private String schemaName;
    private String version;
    private String schemaType;
    private String schemaId;
    private String lockType;

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

    public String getSchemaType() {
        return schemaType;
    }

    public void setSchemaType(String schemaType) {
        this.schemaType = schemaType;
    }

    public String getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(String schemaId) {
        this.schemaId = schemaId;
    }

    public String getLockType() {
        return lockType;
    }

    public void setLockType(String lockType) {
        this.lockType = lockType;
    }

}
