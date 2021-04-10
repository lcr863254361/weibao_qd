package com.orient.background.bean;

import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.web.modelDesc.column.ColumnDesc;

import java.io.Serializable;

/**
 * Created by enjoy on 2016/3/19 0019.
 * 对原来的column描述增加树形属性
 */
public class OrientTreeColumnDesc extends ColumnDesc implements Serializable{

    private Boolean leaf = true;

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    @Override
    public void specialInit(IBusinessColumn iBusinessColumn) {

    }
}
