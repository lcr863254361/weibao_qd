package com.orient.dsrestful.domain.lock;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-03-24 11:05
 */
public class SchemaLockBean implements Serializable{

    private String schemaName;
    private String version;
    private String username;
    private String ip;
    private int lockTag;   //0解锁 1上锁

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getLockTag() {
        return lockTag;
    }

    public void setLockTag(int lockTag) {
        this.lockTag = lockTag;
    }

}
