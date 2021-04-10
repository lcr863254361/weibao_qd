package com.orient.modeldata.bean;

import java.io.Serializable;

/**
 * 静态Tbom节点
 *
 * @author enjoy
 * @creare 2016-05-18 15:37
 */
public class TBomStaticNode extends TBomNode implements Serializable {

    //默認順序
    private Long order = 1l;

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public TBomStaticNode() {
        this.setNodeType(super.STATIC_NODE);
    }
}
