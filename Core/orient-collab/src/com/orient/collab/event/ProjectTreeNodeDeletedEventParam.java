package com.orient.collab.event;

import com.orient.collab.model.TreeDeleteResult;
import com.orient.web.base.OrientEventBus.OrientEventParams;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-07-15 上午10:49
 */
public class ProjectTreeNodeDeletedEventParam extends OrientEventParams {

    public ProjectTreeNodeDeletedEventParam(String modelName, String dataId, TreeDeleteResult treeDeleteResult){
        this.modelName = modelName;
        this.dataId = dataId;
        this.treeDeleteResult = treeDeleteResult;
    }

    private final String modelName;
    private final String dataId;
    private TreeDeleteResult treeDeleteResult;

    public String getModelName() {
        return modelName;
    }

    public String getDataId() {
        return dataId;
    }

    public TreeDeleteResult getTreeDeleteResult() {
        return treeDeleteResult;
    }

    public void setTreeDeleteResult(TreeDeleteResult treeDeleteResult) {
        this.treeDeleteResult = treeDeleteResult;
    }
}
