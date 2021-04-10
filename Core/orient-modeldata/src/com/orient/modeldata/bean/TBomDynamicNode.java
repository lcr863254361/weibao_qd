package com.orient.modeldata.bean;

import java.io.Serializable;

/**
 * 静态Tbom节点
 *
 * @author enjoy
 * @creare 2016-05-18 15:37
 */
public class TBomDynamicNode extends TBomNode implements Serializable{

    String dynamicId;

    public String getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }

    public TBomDynamicNode(){
         this.setNodeType(super.DYNAMIC_NODE);
     }
}
