package com.orient.collab.model;

/**
 * CollabFlowJpdlInfo
 *
 * @author Seraph
 *         2016-08-22 上午9:30
 */
public class CollabFlowJpdlInfo {

    public CollabFlowJpdlInfo(){
    }
    public CollabFlowJpdlInfo(String rootModelName, String rootDataId, String rootDisplayName){
        this.rootModelName = rootModelName;
        this.rootDataId = rootDataId;
        this.rootDisplayName = rootDisplayName;
    }

    private boolean success;
    private String jpdl;
    private String rootModelName;
    private String rootDataId;
    private String rootDisplayName;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getJpdl() {
        return jpdl;
    }

    public void setJpdl(String jpdl) {
        this.jpdl = jpdl;
    }

    public String getRootModelName() {
        return rootModelName;
    }

    public void setRootModelName(String rootModelName) {
        this.rootModelName = rootModelName;
    }

    public String getRootDataId() {
        return rootDataId;
    }

    public void setRootDataId(String rootDataId) {
        this.rootDataId = rootDataId;
    }

    public String getRootDisplayName() {
        return rootDisplayName;
    }

    public void setRootDisplayName(String rootDisplayName) {
        this.rootDisplayName = rootDisplayName;
    }
}
