package com.orient.dsrestful.domain.login;

/**
 * Created by GNY on 2018/3/24
 */
public class LoginAndLockSchemaBean extends LoginRequestBean {

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
