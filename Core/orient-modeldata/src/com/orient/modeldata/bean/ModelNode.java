package com.orient.modeldata.bean;

/**
 * Created by Administrator on 2017/4/6 0006.
 */
public class ModelNode extends BaseNode{

    private String modelDBName;


    public String getModelDBName() {
        return modelDBName;
    }

    public void setModelDBName(String modelDBName) {
        this.modelDBName = modelDBName;
    }

    public ModelNode(String id, String text, String iconCls, Boolean leaf, Boolean expanded, String modelDBName) {
        super(id, text, iconCls, leaf, expanded);
        this.modelDBName = modelDBName;
    }

    public ModelNode(String id, String text, String iconCls, Boolean leaf, String modelDBName) {
        super(id, text, iconCls, leaf);
        this.modelDBName = modelDBName;
    }
}
