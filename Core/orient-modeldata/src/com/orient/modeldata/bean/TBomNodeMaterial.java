package com.orient.modeldata.bean;

import java.io.Serializable;

/**
 * 构建Tbom节点的素材
 *
 * @author enjoy
 * @createTime 2016-05-23 15:10
 */
public class TBomNodeMaterial implements Serializable{

    //最近兄弟静态节点ID
    protected String staticDbId;

    //所属Tbom ID
    protected String treeDirId;

    //前端父节点描述
    protected TBomNode parentNode;

    public String getStaticDbId() {
        return staticDbId;
    }

    public String getTreeDirId() {
        return treeDirId;
    }

    public TBomNode getParentNode() {
        return parentNode;
    }

    public TBomNodeMaterial(String staticDbId, String treeDirId, TBomNode parentNode) {
        this.staticDbId = staticDbId;
        this.treeDirId = treeDirId;
        this.parentNode = parentNode;
    }
}
