package com.orient.modeldata.bean;

import com.orient.sysmodel.operationinterface.IDynamicTbom;
import com.orient.sysmodel.operationinterface.ITbom;

import java.io.Serializable;

/**
 * 构建动态Tbom节点的素材
 *
 * @author enjoy
 * @createTime 2016-05-23 15:10
 */
public class TBomDynamicNodeMaterial extends TBomNodeMaterial implements Serializable {

    //动态子节点后台描述
    private IDynamicTbom hibernateDynamicTbomNode;


    //动态父节点后台描述
    private IDynamicTbom fatherHibernateDynamicNode;

    //静态父节点后台描述
    private ITbom fatherHibernateStaticNode;

    public IDynamicTbom getHibernateDynamicTbomNode() {
        return hibernateDynamicTbomNode;
    }

    public IDynamicTbom getFatherHibernateDynamicNode() {
        return fatherHibernateDynamicNode;
    }

    public ITbom getFatherHibernateStaticNode() {
        return fatherHibernateStaticNode;
    }

    public TBomDynamicNodeMaterial(String staticDbId, String treeDirId, TBomNode parentNode, IDynamicTbom hibernateDynamicTbomNode, IDynamicTbom fatherHibernateDynamicNode, ITbom fatherHibernateStaticNode) {
        super(staticDbId, treeDirId, parentNode);
        this.hibernateDynamicTbomNode = hibernateDynamicTbomNode;
        this.fatherHibernateDynamicNode = fatherHibernateDynamicNode;
        this.fatherHibernateStaticNode = fatherHibernateStaticNode;
    }
}
