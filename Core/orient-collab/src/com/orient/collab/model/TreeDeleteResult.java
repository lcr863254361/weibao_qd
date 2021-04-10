package com.orient.collab.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-12-29 14:57
 */
public class TreeDeleteResult implements Serializable {

    private Boolean deleteSuccess = false;

    private List<TreeDeleteTarget> treeDeleteTargets = new ArrayList<>();

    private String errorMsg = "删除成功!";

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Boolean getDeleteSuccess() {
        return deleteSuccess;
    }

    public void setDeleteSuccess(Boolean deleteSuccess) {
        this.deleteSuccess = deleteSuccess;
    }

    public List<TreeDeleteTarget> getTreeDeleteTargets() {
        return treeDeleteTargets;
    }

    public void setTreeDeleteTargets(List<TreeDeleteTarget> treeDeleteTargets) {
        this.treeDeleteTargets = treeDeleteTargets;
    }

    public void addTreeDeleteTarget(String modelName, String dataId) {
        TreeDeleteTarget treeDeleteTarget = new TreeDeleteTarget(modelName, dataId);
        this.getTreeDeleteTargets().add(treeDeleteTarget);
    }
}
