package com.orient.template.model;

import java.io.Serializable;

/**
 * represent a collab flow independent component
 *
 * @author Seraph
 *         2016-10-08 下午2:53
 */
public class CollabFlow implements Serializable {

    private boolean hasPd;
    private String bindModelName;
    private String bindDataId;
    private byte[] jpdlContent;

    public CollabFlow(String bindModelName, String bindDataId){
        this.bindModelName = bindModelName;
        this.bindDataId = bindDataId;
    }

    public byte[] getJpdlContent() {
        return jpdlContent;
    }

    public void setJpdlContent(byte[] jpdlContent) {
        this.jpdlContent = jpdlContent;
    }

    public boolean isHasPd() {
        return hasPd;
    }

    public void setHasPd(boolean hasPd) {
        this.hasPd = hasPd;
    }

    private static final long serialVersionUID =  1L;

    public String getBindModelName() {
        return bindModelName;
    }

    public void setBindModelName(String bindModelName) {
        this.bindModelName = bindModelName;
    }

    public String getBindDataId() {
        return bindDataId;
    }

    public void setBindDataId(String bindDataId) {
        this.bindDataId = bindDataId;
    }
}
