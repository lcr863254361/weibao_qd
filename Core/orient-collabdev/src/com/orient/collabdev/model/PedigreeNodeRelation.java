package com.orient.collabdev.model;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-15 10:28 AM
 */
public class PedigreeNodeRelation implements Serializable {

    private String id;
    private String srcDevNodeId;
    private String destDevNodeId;
    private String pid;
    private Integer pversion;
    private String rid;
    private Integer rversion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrcDevNodeId() {
        return srcDevNodeId;
    }

    public void setSrcDevNodeId(String srcDevNodeId) {
        this.srcDevNodeId = srcDevNodeId;
    }

    public String getDestDevNodeId() {
        return destDevNodeId;
    }

    public void setDestDevNodeId(String destDevNodeId) {
        this.destDevNodeId = destDevNodeId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getPversion() {
        return pversion;
    }

    public void setPversion(Integer pversion) {
        this.pversion = pversion;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public Integer getRversion() {
        return rversion;
    }

    public void setRversion(Integer rversion) {
        this.rversion = rversion;
    }
}
