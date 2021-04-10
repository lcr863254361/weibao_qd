package com.orient.modeldata.bean;

import com.orient.sysmodel.operationinterface.ITbom;

import java.io.Serializable;

/**
 * 构建静态Tbom节点的素材
 *
 * @author enjoy
 * @createTime 2016-05-23 15:10
 */
public class TBomStaticNodeMaterial extends TBomNodeMaterial implements Serializable {

    //静态节点数据库描述
    private ITbom hibernateTbomNode;

    public TBomStaticNodeMaterial(String staticDbId, String treeDirId, TBomNode parentNode, ITbom hibernateTbomNode) {
        super(staticDbId, treeDirId, parentNode);
        this.hibernateTbomNode = hibernateTbomNode;
    }

    public ITbom getHibernateTbomNode() {
        return hibernateTbomNode;
    }
}
